const { createApp } = Vue;

// URL base del API
const API_URL = 'http://localhost:8080/api';

createApp({
  data() {
    return {
      // Login
      autenticado: false,
      usuarioActual: {},
      credenciales: {
        usuario: '',
        contraseña: ''
      },
      loginError: '',
      
      // Admin
      adminTab: 'productos',
      
      // Datos
      productos: [],
      clientes: [],
      ventas: [],
      
      // Formulario nuevo producto
      nuevoProducto: {
        codigo: '',
        nombre: '',
        categoria: '',
        precio: 0,
        stock: 0,
        talla: '',
        color: ''
      },
      
      // Estado general
      cargando: false,
      mensaje: { tipo: '', texto: '' }
    }
  },

  methods: {
    // ===== LOGIN =====
    async login() {
      if (!this.credenciales.usuario || !this.credenciales.contraseña) {
        this.loginError = 'Usuario y contraseña son requeridos';
        return;
      }

      this.cargando = true;
      this.loginError = '';

      try {
        const response = await fetch(`${API_URL}/auth/login`, {
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
            usuario: data.usuario,
            rol: data.rol
          };
          
          // Guardar en localStorage
          localStorage.setItem('usuarioActual', JSON.stringify(this.usuarioActual));
          
          // Cargar datos
          await this.cargarProductos();
          await this.cargarClientes();
          await this.cargarVentas();
          
          this.mostrarMensaje('exito', `Bienvenido ${data.nombre}`);
        } else {
          this.loginError = data.error || 'Error en la autenticación';
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
        this.credenciales = { usuario: '', contraseña: '' };
        localStorage.removeItem('usuarioActual');
        this.mostrarMensaje('info', 'Sesión cerrada');
      }
    },

    // ===== MENSAJES =====
    mostrarMensaje(tipo, texto) {
      this.mensaje = { tipo, texto };
      setTimeout(() => {
        this.mensaje = { tipo: '', texto: '' };
      }, 3000);
    },

    // ===== PRODUCTOS =====
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
      try {
        const response = await fetch(`${API_URL}/productos`);
        if (!response.ok) {
          throw new Error('Error al cargar productos');
        }
        this.productos = await response.json();
      } catch (error) {
        console.error('Error:', error);
        this.mostrarMensaje('error', 'No se pudo cargar los productos');
      }
    },

    // ===== CLIENTES =====
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
      try {
        const response = await fetch(`${API_URL}/clientes`);
        if (!response.ok) {
          throw new Error('Error al cargar clientes');
        }
        this.clientes = await response.json();
      } catch (error) {
        console.error('Error:', error);
        this.clientes = [];
      }
    },

    // ===== VENTAS =====
    async cargarVentas() {
      try {
        const response = await fetch(`${API_URL}/ventas`);
        if (!response.ok) {
          throw new Error('Error al cargar ventas');
        }
        this.ventas = await response.json();
      } catch (error) {
        console.error('Error:', error);
        this.ventas = [];
      }
    },

    // ===== UTILIDADES =====
    formatoFecha(fecha) {
      return new Date(fecha).toLocaleDateString('es-PE');
    },

    // Verificar si hay sesión guardada
    verificarSesion() {
      const usuarioGuardado = localStorage.getItem('usuarioActual');
      if (usuarioGuardado) {
        try {
          this.usuarioActual = JSON.parse(usuarioGuardado);
          this.autenticado = true;
          this.cargarProductos();
          this.cargarClientes();
          this.cargarVentas();
        } catch (e) {
          console.error('Error al restaurar sesión');
        }
      }
    }
  },

  mounted() {
    this.verificarSesion();
  }

}).mount('#app');
