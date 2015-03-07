ReaderView = Backbone.Marionette.CompositeView.extend({
    id: 'ReaderView',
    className: 'ReaderView',
    template: '#ReaderTemplate',
    itemView: PageView,
    currentPage: 1,

    initialize: function(){
                var self = this;
                this.model;
                var models = []
                this.collection = new PageCollection();
                for(var i=1; i<=this.model.attributes.numPages; i++){
                    models.push(new PageModel({
                        id: i,
                        comicBookId: this.model.id,
                        hide: self.currentPage==i?false:true
                    }));
                }
                this.collection.add(models);

        self.setupNavButtons();

        $(document).bind('keydown', 'left', function(){self.prevPanel(self);});
        $(document).bind('keydown', 'right', function(){self.nextPanel(self);});
        $(document).bind('keydown', 'v', function(){self.fitVertical();});
        $(document).bind('keydown', 'h', function(){self.fitHorizontal();});
        $(document).bind('keydown', 'b', function(){self.fitBoth();});
        $("#comicImages img").bind('swipeleft', function(){self.prevPanel(self);});
        $("#comicImages img").bind('swiperight', function(){self.nextPanel(self);});
    },

    setupNavButtons: function(){
        var self = this;

        $("#fitVertical").parent().show();
        $("#fitVertical").click(self.fitVertical);
        $("#fitHorizontal").parent().show();
        $("#fitHorizontal").click(self.fitHorizontal);
        $("#fitBoth").parent().show();
        $("#fitBoth").click(self.fitBoth);
        $("#fitVerticalxs").parent().show();
        $("#fitVerticalxs").click(self.fitVertical);
        $("#fitHorizontalxs").parent().show();
        $("#fitHorizontalxs").click(self.fitHorizontal);
        $("#fitBothxs").parent().show();
        $("#fitBothxs").click(self.fitBoth);
    },

    appendHtml: function(collectionView, itemView){
        var self = this;
        collectionView.$("#comicImages").append(itemView.el);
        self.setupTouchControls(itemView.el);
    },

    setupTouchControls: function(item){
        var self = this;
        //$(item).bind('tap', function(){self.nextPanel(self);});
        $(item).bind('click', function(){self.nextPanel(self);});
        $(item).bind('swiperight', function(){self.nextPanel(self);});
        $(item).bind('swipeleft', function(){self.prevPanel(self);});
    },

    nextPanel: function(self){
        if(self.currentPage<self.model.attributes.numPages){
            self.drawPanel(++self.currentPage);
        }
    },

    prevPanel: function(self){
        if(self.currentPage>1){
            self.drawPanel(--self.currentPage);
        }
    },

    drawPanel: function(id){
        this.currentPage=id;
        for(var i=0;i<this.model.attributes.numPages;i++){
            $('#page-'+i).hide();
        }
        $('#page-'+id).show();
    },

    fitHorizontal: function() {
      $("#comicImages img").removeClass();
      $("#comicImages img").addClass('fitHorizontal');
    },

    fitVertical: function() {
      $("#comicImages img").removeClass();
      $("#comicImages img").addClass('fitVertical');
    },

    fitBoth: function() {
      $("#comicImages img").removeClass();
      $("#comicImages img").addClass('fitBoth');
    },




    loadPages : function(){
    	if(model.numPages>0){
    		for(var i = 1; i<= comicJson.numPages; i++){
    		    images.push({loaded:true, path: "/page/"+comicJson.id+"/"+i+"/"});
    		}
    	}
    }
});