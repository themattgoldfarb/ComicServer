UsersView = Backbone.Marionette.CompositeView.extend({

    tagName: 'div',
    id: 'UsersView',
    className: 'UsersView',
    template: '#UsersTemplate',
    itemView: UserView,

    initialize: function(){
        var self = this;

        self.collection = new Backbone.Collection();
        self.collection.url = '/users';

        self.refreshUsers(self);
    },

    refreshUsers: function(self) {
        self.collection.fetch({
            success: function () {
                self.render();
            }
        });
    },

    appendHtml: function(collectionView, itemView){
    collectionView.$("#UsersView").append(itemView.el);
    }

});