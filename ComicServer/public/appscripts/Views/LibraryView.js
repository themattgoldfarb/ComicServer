LibraryView = Backbone.Marionette.CompositeView.extend({
    tagName: 'div',
    id: 'LibraryView',
    className: 'LibraryView',
    template: '#LibraryTemplate',
    itemView: ComicView,

    initialize: function(){
        self = this;

        $(document).on('change:library', function(){self.refreshLibrary(self);});
    },

    refreshLibrary : function(self){
        self.collection.fetch({
            success:function(){self.render()},
        });
    },

    appendHtml: function(collectionView, itemView){
        collectionView.$("#LibraryView").append(itemView.el);
    }

});