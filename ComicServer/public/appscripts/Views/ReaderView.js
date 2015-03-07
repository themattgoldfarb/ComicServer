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
        $(document).bind('keydown', 'f', function(){self.hideTop();});
        $(document).bind('keydown', 'esc', function(){self.showTop();});

    },

    setupNavButtons: function(){
        var self = this;

        $("#fitVertical").parent().show();
        $("#fitVertical").click(self.fitVertical);
        $("#fitHorizontal").parent().show();
        $("#fitHorizontal").click(self.fitHorizontal);
        $("#fitBoth").parent().show();
        $("#fitBoth").click(self.fitBoth);
        $("#hideTop").parent().show();
        $("#hideTop").click(self.hideTop);
        $("#toggleTapTarget").parent().show();
        $("#toggleTapTarget").click(self.toggleTapTarget);
        $("#fitVerticalxs").parent().show();
        $("#fitVerticalxs").click(self.fitVertical);
        $("#fitHorizontalxs").parent().show();
        $("#fitHorizontalxs").click(self.fitHorizontal);
        $("#fitBothxs").parent().show();
        $("#fitBothxs").click(self.fitBoth);
        $("#hideTopxs").parent().show();
        $("#hideTopxs").click(self.hideTop);
        $("#hideTopxs").click(self.hideTop);
        $("#toggleTapTargetxs").parent().show();
        $("#toggleTapTargetxs").click(self.toggleTapTarget);
    },

    appendHtml: function(collectionView, itemView){
        var self = this;
        collectionView.$("#comicImages").append(itemView.el);
        self.setupTouchControls(itemView.el);
    },

    onRender: function(collectionView){
        var self = this;
        collectionView.$('#topButton').bind('tap', function(){self.toggleTop();});
        collectionView.$('#leftButton').bind('tap', function(){self.prevPanel(self);});
        collectionView.$('#rightButton').bind('tap', function(){self.nextPanel(self);});
        collectionView.$('#toggleControlsButton').bind('tap', function(){self.toggleTapTarget();});
        collectionView.$('#fitBothButton').bind('tap', function(){self.fitBoth();});
        collectionView.$('#fitVerticalButton').bind('tap', function(){self.fitVertical();});
        collectionView.$('#fitHorizontalButton').bind('tap', function(){self.fitHorizontal();});
    },

    setupTouchControls: function(item){
        var self = this;
        //$(item).bind('tap', function(){self.nextPanel(self);});
       // $(item).bind('click', function(){self.nextPanel(self);});
        $(item).bind('swipeleft', function(){self.nextPanel(self);});
        $(item).bind('swiperight', function(){self.prevPanel(self);});
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

    hideTop: function() {
        $("#main").removeClass('show-top-bar');
    },

    showTop: function() {
        $("#main").addClass('show-top-bar');
    },

    toggleTop: function() {
        $("#main").toggleClass('show-top-bar');
    },

    toggleTapTarget: function() {
        $("#main").toggleClass('show-tap-target');
    },

    loadPages : function(){
    	if(model.numPages>0){
    		for(var i = 1; i<= comicJson.numPages; i++){
    		    images.push({loaded:true, path: "/page/"+comicJson.id+"/"+i+"/"});
    		}
    	}
    }
});