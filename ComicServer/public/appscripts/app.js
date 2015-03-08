window.app = new Backbone.Marionette.Application();

app.addRegions({
    mainRegion: "#main-container",
    sideBarRegion: "#side-bar-container",
    touchControlsRegion: "#touch-controls-container"
})

app.on('initialize:after', function(){
    Backbone.history.start();

})

app.addInitializer(function(options) {
    app.library = options.library;

    app.showSideBar(options);

        app.addView = new AddView({
        });
})

app.showLibrary = function(options){
    app.libraryView = new LibraryView({
        collection: app.library
    });
    app.mainRegion.show(app.libraryView);
}

app.showSideBar = function(options){
    var sideBarView = new SideBarView({});
    app.sideBarRegion.show(sideBarView);
}

app.showReader = function(id){
    var touchControlsView = new TouchControlsView({});
    app.touchControls = touchControlsView;
    app.touchControlsRegion.show(touchControlsView);

    var readerView = new ReaderView({
        model: app.library.get(id),
        touchControls: touchControlsView
    });
    app.reader=readerView;
    app.mainRegion.show(readerView);
}

app.showAdd = function(){
    app.addView = new AddView();
    app.addView.refreshLibrary = function(){ app.showLibrary(); };
    app.mainRegion.show(app.addView);
}

app.showUsers = function(){
    app.usersView = new UsersView()
    app.mainRegion.show(app.usersView);
}

router = new Backbone.Marionette.AppRouter({
    controller: app,
    appRoutes: {
        "reader/:id/" : "showReader",
        "library" : "showLibrary",
        "add" : "showAdd",
        "users" : "showUsers"
    }
});
