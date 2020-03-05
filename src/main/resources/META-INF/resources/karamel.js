// Show snackbar
function showSnackbar() {
  var x = document.getElementById("snackbar");
  x.className = "show";
  setTimeout(function(){ x.className = x.className.replace("show", ""); }, 2000);
}
// find JSESSIONID
function getJSessionId(){
    var jsId = document.cookie.match(/JSESSIONID=[^;]+/);
    if(jsId != null) {
        if (jsId instanceof Array)
            jsId = jsId[0].substring(11);
        else
            jsId = jsId.substring(11);
    }
    return jsId;
}

// Vue app
var client = new Vue({
  el: '#app',
  data: {
    connected: false,
    socket: null,
    bootstrapShow: false,
    brokers: '',
    consumerVis: true,
    producerVis: false,
    topic: '',
    key: '',
    value: ''
  },
  mounted:function(){
    console.log("LOADED");
    this.onLoadPage();
    },
  methods: {
      onLoadPage: function (event) {
        var str = window.location.pathname;
        var isClient = str.startsWith("/client");
        if (isClient) {
            console.log(isClient);
            this.onSocketConnect();
        }
      },
      onSocketConnect: function() {
        if (! this.connected) {
            this.socket = new WebSocket("ws://" + location.host + "/events/" + getJSessionId());
            this.socket.onopen = function() {
                this.connected = true;
                console.log("Connected to the web socket");
            };
            this.socket.onmessage = function(m) {
                console.log(m);
                location.reload();
            };
        }
      },
      onDropDownBroker: function (event) {
        this.bootstrapShow = !this.bootstrapShow
      },
      onSelectBroker: function (broker) {
        this.brokers = broker;
        this.bootstrapShow = false;
      },
      onSubmitBroker: function(e) {
        var formAction = e.target.action;
      },
      showConsumer: function (event) {
        this.consumerVis = true
        this.producerVis = false
      },
      showProducer: function (event) {
        this.consumerVis = false
        this.producerVis = true
      },
      onPublish: function (event) {
          axios.post('/message', {
              topic: this.topic,
              key: this.key,
              value: this.value
            })
            .then(function (response) {
              console.log(response);
              showSnackbar();
            })
            .catch(function (error) {
              console.log(error);
            });
      }
    },
});