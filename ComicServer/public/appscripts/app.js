window.app = new Backbone.Marionette.Application();

app.addRegions({
    mainRegion: "#main-container"
})

app.on('initialize:after', function(){
    Backbone.history.start();

})

app.addInitializer(function(options) {
    var libraryView = new LibraryView({
        collection: options.library
    });
    app.mainRegion.show(libraryView);
})

