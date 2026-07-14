const { createApp } = Vue;

// URL base del API
const API_URL = 'https://urbanflow-2anh.onrender.com/api';

createApp({
  data() {
    return {
      vercion: 'inicio',
      adminTab: 'productos',
      busqueda: '',
      filtroCategoria: '',
      filtroPrecioMax: 500,
      filtroTallas: [],
      ordenar: 'nuevo',
      carrito: [],
      
      productos: [],
      clientes: [],
      ventas: [],
      
      nuevoProducto: {
        codigo: '',
        nombre: '',
        categoria: '',
        precio: 0,
        stock: 0,
        talla: '',
        color: ''
      },
      
      costoEnvio: 15.00,
      impuesto: 0,
      cargando: false,
      mensaje: { tipo: '', texto: '' }
    }
  },

  computed: {
    productosFiltrados() {
      const productos = this.productos.filter(p => {
        const cumpleCategoria = !this.filtroCategoria || p.categoria === this.filtroCategoria;
        const textoBusqueda = String(this.busqueda || '').trim().toUpperCase();
        const tallaProducto = String(p.talla || '').trim().toUpperCase();
        const cumpleBusqueda = !textoBusqueda ||
          String(p.nombre || '').toUpperCase().includes(textoBusqueda) ||
          String(p.categoria || '').toUpperCase().includes(textoBusqueda) ||
          tallaProducto.includes(textoBusqueda);
        const precioMax = Number(this.filtroPrecioMax) || 500;
        const cumplePrecio = Number(p.precio || 0) <= precioMax;
        const tallasSeleccionadas = (this.filtroTallas || []).map(talla => String(talla || '').trim().toUpperCase());
        const cumpleTalla = tallasSeleccionadas.length === 0 || tallasSeleccionadas.includes(tallaProducto);

        return cumpleCategoria && cumpleBusqueda && cumplePrecio && cumpleTalla;
      });

      const productosOrdenados = productos.slice();

      if (this.ordenar === 'precio-asc') {
        productosOrdenados.sort((a, b) => Number(a.precio || 0) - Number(b.precio || 0));
      } else if (this.ordenar === 'precio-desc') {
        productosOrdenados.sort((a, b) => Number(b.precio || 0) - Number(a.precio || 0));
      } else if (this.ordenar === 'nombre') {
        productosOrdenados.sort((a, b) => String(a.nombre || '').localeCompare(String(b.nombre || '')));
      }

      return productosOrdenados;
    },

    subtotal() {
      return this.carrito.reduce((sum, item) => sum + (item.precio * item.cantidad), 0);
    },

    total() {
      this.impuesto = this.subtotal * 0.16;
      return this.subtotal + this.costoEnvio + this.impuesto;
    }
  },

  methods: {
    // Mostrar mensajes
    mostrarMensaje(tipo, texto) {
      this.mensaje = { tipo, texto };
      setTimeout(() => {
        this.mensaje = { tipo: '', texto: '' };
      }, 3000);
    },

    limpiarFiltros() {
      this.filtroCategoria = '';
      this.filtroPrecioMax = 500;
      this.filtroTallas = [];
      this.ordenar = 'nuevo';
      this.busqueda = '';
    },

    // Funciones del carrito
    agregarAlCarrito(producto) {
      const itemExistente = this.carrito.find(item => item.id === producto.id);
      
      if (itemExistente) {
        itemExistente.cantidad++;
      } else {
        this.carrito.push({
          ...producto,
          cantidad: 1
        });
      }
      
      this.mostrarMensaje('exito', `${producto.nombre} agregado al carrito`);
      this.guardarCarrito();
    },

    eliminarDelCarrito(index) {
      const producto = this.carrito[index];
      if (confirm(`¿Eliminar ${producto.nombre} del carrito?`)) {
        this.carrito.splice(index, 1);
        this.guardarCarrito();
        this.mostrarMensaje('info', 'Producto eliminado del carrito');
      }
    },

    aumentarCantidad(index) {
      this.carrito[index].cantidad++;
      this.guardarCarrito();
    },

    disminuirCantidad(index) {
      if (this.carrito[index].cantidad > 1) {
        this.carrito[index].cantidad--;
        this.guardarCarrito();
      }
    },

      completarCompra() {
      if (this.carrito.length === 0) {
        this.mostrarMensaje('error', 'El carrito está vacío');
        return;
      }

      this.cargando = true;
      try {
        // Aquí iría la integración con Venta en el backend
        this.mostrarMensaje('exito', `Compra completada. Total: S/ ${this.total.toFixed(2)}`);
        this.carrito = [];
        this.guardarCarrito();
        this.vercion = 'inicio';
      } catch (error) {
        this.mostrarMensaje('error', 'Error al procesar la compra');
      } finally {
        this.cargando = false;
      }
    },

    guardarCarrito() {
      localStorage.setItem('carrito', JSON.stringify(this.carrito));
    },

    cargarCarrito() {
      const carritoGuardado = localStorage.getItem('carrito');
      if (carritoGuardado) {
        this.carrito = JSON.parse(carritoGuardado);
      }
    },

    // Funciones Admin - Productos
    async agregarProductoAdmin() {
      if (!this.nuevoProducto.nombre || !this.nuevoProducto.precio) {
        this.mostrarMensaje('error', 'Por favor completa todos los campos');
        return;
      }

      this.cargando = true;
      try {
        const response = await fetch(`${API_URL}/productos`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            codigo: this.nuevoProducto.codigo || `PROD${Date.now()}`,
            nombre: this.nuevoProducto.nombre,
            categoria: this.nuevoProducto.categoria || 'General',
            precio: parseFloat(this.nuevoProducto.precio),
            stock: parseInt(this.nuevoProducto.stock) || 0,
            talla: this.nuevoProducto.talla || 'M',
            color: this.nuevoProducto.color || 'Negro'
          })
        });

        if (!response.ok) {
          const error = await response.text();
          this.mostrarMensaje('error', error);
          return;
        }

        await this.cargarProductos();
        this.nuevoProducto = {
          codigo: '',
          nombre: '',
          categoria: '',
          precio: 0,
          stock: 0,
          talla: '',
          color: ''
        };

        this.mostrarMensaje('exito', 'Producto agregado correctamente');
      } catch (error) {
        this.mostrarMensaje('error', `Error: ${error.message}`);
      } finally {
        this.cargando = false;
      }
    },

    async eliminarProductoAdmin(id) {
      if (confirm('¿Estás seguro de que quieres eliminar este producto?')) {
        this.cargando = true;
        try {
          const response = await fetch(`${API_URL}/productos/${id}`, {
            method: 'DELETE'
          });

          if (!response.ok) {
            this.mostrarMensaje('error', 'Error al eliminar el producto');
            return;
          }

          await this.cargarProductos();
          this.mostrarMensaje('exito', 'Producto eliminado');
        } catch (error) {
          this.mostrarMensaje('error', `Error: ${error.message}`);
        } finally {
          this.cargando = false;
        }
      }
    },

    async cargarProductos() {
      this.cargando = true;
      try {
        const response = await fetch(`${API_URL}/productos`);
        if (!response.ok) {
          throw new Error('Error al cargar productos');
        }
        this.productos = await response.json();
      } catch (error) {
        console.error('Error:', error);
        this.mostrarMensaje('error', 'No se pudo conectar con el servidor');
      } finally {
        this.cargando = false;
      }
    },

    // Funciones Admin - Clientes
    async eliminarClienteAdmin(id) {
      if (confirm('¿Eliminar este cliente?')) {
        this.cargando = true;
        try {
          const response = await fetch(`${API_URL}/clientes/${id}`, {
            method: 'DELETE'
          });

          if (!response.ok) {
            this.mostrarMensaje('error', 'Error al eliminar el cliente');
            return;
          }

          await this.cargarClientes();
          this.mostrarMensaje('exito', 'Cliente eliminado');
        } catch (error) {
          this.mostrarMensaje('error', `Error: ${error.message}`);
        } finally {
          this.cargando = false;
        }
      }
    },

    async cargarClientes() {
      this.cargando = true;
      try {
        const response = await fetch(`${API_URL}/clientes`);
        if (!response.ok) {
          throw new Error('Error al cargar clientes');
        }
        this.clientes = await response.json();
      } catch (error) {
        console.error('Error:', error);
        this.clientes = [];
      } finally {
        this.cargando = false;
      }
    },

    async cargarVentas() {
      this.cargando = true;
      try {
        const response = await fetch(`${API_URL}/ventas`);
        if (!response.ok) {
          throw new Error('Error al cargar ventas');
        }
        this.ventas = await response.json();
      } catch (error) {
        console.error('Error:', error);
        this.ventas = [];
      } finally {
        this.cargando = false;
      }
    },

    formatoFecha(fecha) {
      return new Date(fecha).toLocaleDateString('es-ES');
    }
  },

  mounted() {
    this.cargarProductos();
    this.cargarClientes();
    this.cargarVentas();
    this.cargarCarrito();

    this.onProductosSync = () => {
      this.cargarProductos();
    };

    window.addEventListener('storage', this.onProductosSync);

    this.productosRefreshTimer = setInterval(() => {
      this.cargarProductos();
    }, 10000);
  },

  beforeUnmount() {
    if (this.productosRefreshTimer) {
      clearInterval(this.productosRefreshTimer);
    }

    if (this.onProductosSync) {
      window.removeEventListener('storage', this.onProductosSync);
    }
  }

}).mount('#app');
