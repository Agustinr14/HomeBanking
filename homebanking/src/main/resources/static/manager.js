const app = new Vue({
    el: '#app',
    data: {
        email: "",
        nombre: "",
        apellido: "",
        outPut: "",
        clients: []
    },
    methods:{
        // load and display JSON sent by server for /clients
        loadData: function() {
            axios.get("/rest/clients")
            .then(function (response) {
                // handle success
                app.outPut = response.data;
                app.clients = response.data._embedded.clients;
            })
            .catch(function (error) {
                // handle error
                app.outPut = error;
            })
        },
         // handler for when user clicks add client
        addClient: function() {
            if (app.email.length > 1 && app.nombre.length > 1 && app.apellido.length > 1) {
                this.postClient(app.email,app.nombre,app.apellido);
            }
        },
        // code to post a new client using AJAX
        // on success, reload and display the updated data from the server
        postClient: function(email, nombre, apellido) {
             axios.post("/rest/clients",{ "email":email, "nombre": nombre, "apellido": apellido })
            .then(function (response) {
                // handle success
                showOutput = "Saved -- reloading";
                app.loadData();
                app.clearData();
            })
            .catch(function (error) {
                // handle error
                app.outPut = error;
            })
        },
        clearData: function(){
            app.nombre = "";
            app.apellido = "";
            app.email = "";
        }
    },
    mounted(){
        this.loadData();
    }
});

