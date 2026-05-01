const { createApp } = Vue;

// URL base del API
const API_URL = 'http://localhost:8080/api';

createApp({
  data() {
    return {
      // Autenticación
      autenticado: false,
      modoLogin: true,
      usuarioActual: {},
      credenciales: {
        email: '',
        contraseña: ''
      },
      datosRegistro: {
        nombre: '',
        email: '',
        telefono: '',
        ciudad: '',
        contraseña: ''
      },
      loginError: '',
      
      // Ecommerce
      vercion: 'inicio',
      adminTab: 'productos',
      busqueda: '',
      filtroCategoria: '',
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
      return this.productos.filter(p => {
        const cumpleCategoria = !this.filtroCategoria || p.categoria === this.filtroCategoria;
        const cumpleBusqueda = !this.busqueda || 
          p.nombre.toLowerCase().includes(this.busqueda.toLowerCase()) ||
          p.categoria.toLowerCase().includes(this.busqueda.toLowerCase());
        return cumpleCategoria && cumpleBusqueda;
      });
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
    // ===== AUTENTICACIÓN =====
    async login() {
      if (!this.credenciales.email || !this.credenciales.contraseña) {
        this.loginError = 'Email y contraseña son requeridos';
        return;
      }

      this.cargando = true;
      this.loginError = '';

      try {
        const response = await fetch(`${API_URL}/auth/cliente/login`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(this.credenciales)
        });

        const data = await response.json();

        if (response.ok && data.success) {
          this.autenticado = true;
          this.usuarioActual = {
            id: data.id,
            nombre: data.nombre,
            email: data.email,
            telefono: data.telefono
          };
          
          // Guardar en localStorage
          localStorage.setItem('clienteActual', JSON.stringify(this.usuarioActual));
          
          // Cargar datos
          await this.cargarProductos();
          this.vercion = 'inicio';
          
          this.mostrarMensaje('exito', `¡Bienvenido ${data.nombre}!`);
        } else {
          this.loginError = data.error || 'Error en la autenticación';
        }
      } catch (error) {
        this.loginError = `Error de conexión: ${error.message}`;
      } finally {
        this.cargando = false;
      }
    },

    async registro() {
      if (!this.datosRegistro.nombre || !this.datosRegistro.email || 
          !this.datosRegistro.telefono || !this.datosRegistro.ciudad || !this.datosRegistro.contraseña) {
        this.loginError = 'Todos los campos son requeridos';
        return;
      }

      if (this.datosRegistro.contraseña.length < 6) {
        this.loginError = 'La contraseña debe tener al menos 6 caracteres';
        return;
      }

      this.cargando = true;
      this.loginError = '';

      try {
        const response = await fetch(`${API_URL}/auth/cliente/registro`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(this.datosRegistro)
        });

        const data = await response.json();

        if (response.ok && data.success) {
          this.mostrarMensaje('exito', data.mensaje || 'Registro exitoso');
          // Cambiar a login automáticamente
          this.modoLogin = true;
          this.datosRegistro = {
            nombre: '',
            email: '',
            telefono: '',
            ciudad: '',
            contraseña: ''
          };
          this.credenciales.email = data.email;
        } else {
          this.loginError = data.error || 'Error en el registro';
        }
      } catch (error) {
        this.loginError = `Error de conexión: ${error.message}`;
      } finally {
        this.cargando = false;
      }
    },

    logout() {
      if (confirm('¿Cerrar sesión?')) {
        this.autenticado = false;
        this.usuarioActual = {};
        this.credenciales = { email: '', contraseña: '' };
        localStorage.removeItem('clienteActual');
        this.mostrarMensaje('info', 'Sesión cerrada');
      }
    },

    verificarSesion() {
      const clienteGuardado = localStorage.getItem('clienteActual');
      if (clienteGuardado) {
        try {
          this.usuarioActual = JSON.parse(clienteGuardado);
          this.autenticado = true;
          this.cargarProductos();
        } catch (e) {
          console.error('Error al restaurar sesión');
        }
      }
    },

    // ===== MENSAJES =====
    mostrarMensaje(tipo, texto) {
      this.mensaje = { tipo, texto };
      setTimeout(() => {
        this.mensaje = { tipo: '', texto: '' };
      }, 3000);
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
      if (!this.autenticado) {
        this.mostrarMensaje('error', 'Debes iniciar sesión para comprar');
        setTimeout(() => {
          this.vercion = 'inicio';
        }, 1000);
        return;
      }

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
    this.verificarSesion();
    if (this.autenticado) {
      this.cargarProductos();
      this.cargarClientes();
    }
    this.cargarVentas();
    this.cargarCarrito();
  }

}).mount('#app');
