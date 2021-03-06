AddView = Backbone.Marionette.View.extend({
    tagName: 'div',
    id: 'AddView',
    className: 'AddView',
    template: '#AddTemplate',
    model: new FileModel(),

    initialize: function(options){
        var self = this;
        this.model.fetch({success:function(){self.renderModel();}});


    },

    changeModel: function(uri){
        var self = this;
        this.model.urlRoot= '/FileManager/path/' + encodeURIComponent(uri)+'/';
        this.model.fetch({success:function(){self.renderModel();}});
    },

    addDirectory: function(uri){
        var self = this;
        this.model.save(null,{success: function(){$(document).trigger('change:library')}});
    },

    renderModel: function(){
                var self = this;
                var html = Backbone.Marionette.Renderer.render(self.template, self.model.attributes);
                var container = $("#addModal");
                container.html(html);
                container.find(".addFolderRow").click(function(c){
                    self.changeModel($(c.currentTarget).attr('dir'));
                });
                container.find("#addDirectoryButton").click(function(c){
                    self.addDirectory($(c.currentTarget).attr('dir'));
                });
    }

});