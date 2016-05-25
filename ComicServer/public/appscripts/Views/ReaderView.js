ReaderView = Backbone.Marionette.CompositeView.extend({
    id: 'ReaderView',
    className: 'ReaderView',
    template: '#ReaderTemplate',
    itemView: PageView,
    currentPage: 1,
    rotation: 0,
    doublePage: false,

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
        var touchControlsView = self.options.touchControls;
        touchControlsView.$('#topButton').bind('toggle', function(){self.toggleTop();});
        touchControlsView.$('#leftButton').bind('toggle', function(){self.prevPanel(self);});
        touchControlsView.$('#rightButton').bind('toggle', function(){self.nextPanel(self);});
        touchControlsView.$('#toggleControlsButton').bind('toggle', function(){self.toggleTapTarget();});
        touchControlsView.$('#fitBothButton').bind('toggle', function(){self.fitBoth();});
        touchControlsView.$('#fitVerticalButton').bind('toggle', function(){self.fitVertical();});
        touchControlsView.$('#fitHorizontalButton').bind('toggle', function(){self.fitHorizontal();});
        touchControlsView.$('#rotateCWButton').bind('toggle', function(){self.rotateCW();});
        touchControlsView.$('#rotateCCWButton').bind('toggle', function(){self.rotateCCW();});
        touchControlsView.$('#toggleDoublePage').bind('toggle', function(){self.toggleDoublePage();});
        
        $('.scroll-container').unbind('click');
        $('.scroll-container').bind('click', function(event){self.handleClick(event);});
    },

    setupTouchControls: function(item){
        var self = this;
        //$(item).bind('tap', function(){self.nextPanel(self);});
        $(item).bind('swipeleft', function(){self.nextPanel(self);});
        $(item).bind('swiperight', function(){self.prevPanel(self);});
    },

    scrollBottom: function(){
        $('.scroll-container').scrollTop($('#main-container').height() - $('.scroll-container').height());
    },

    scrollTop: function(){
        $('.scroll-container').scrollTop(0);
    },

    nextPanel: function(self){
        if(self.currentPage<self.model.attributes.numPages){
            self.currentPage += self.doublePage ? 2 : 1;
            self.drawPanel(self.currentPage, self.doublePage);
            self.scrollTop();
        }
    },

    prevPanel: function(self){
        if(self.currentPage>1){
            self.currentPage -= self.doublePage ? 2 : 1;
            self.drawPanel(self.currentPage, self.doublePage);
            self.scrollBottom();
        }
    },

    drawPanel: function(id, doublePage){
        this.currentPage=id;
        for(var i=0;i<this.model.attributes.numPages;i++){
            $('#page-'+i).hide();
        }
        $('#page-'+id).show();
        if (doublePage) {
            $('#page-'+(id+1)).show();
        }
    },

    fitHorizontal: function() {
      $("#comicImages img").removeClass('fitVertical');
      $("#comicImages img").removeClass('fitBoth');
      $("#comicImages img").addClass('fitHorizontal');
    },

    fitVertical: function() {
      $("#comicImages img").removeClass('fitHorizontal');
      $("#comicImages img").removeClass('fitBoth');
      $("#comicImages img").addClass('fitVertical');
    },

    fitBoth: function() {
      $("#comicImages img").removeClass('fitHorizontal');
      $("#comicImages img").removeClass('fitVertical');
      $("#comicImages img").addClass('fitBoth');
    },

    clearRotation: function() {
        $("#comicImages img").removeClass('rotate90');
        $("#comicImages img").removeClass('rotate180');
        $("#comicImages img").removeClass('rotate270');
    },

    applyRotation: function(rot) {
        switch (rot) {    
            case 90 :
                $("#comicImages img").addClass('rotate90');
                break;
            case 180 :
                $("#comicImages img").addClass('rotate180');
                break;
            case 270 :
                $("#comicImages img").addClass('rotate270');
        }
    },

    rotateCW: function() {
        var self = this;
        console.log(self.rotation);
        self.rotation = (self.rotation + 90) % 360;
        self.clearRotation();
        self.applyRotation(self.rotation);
    },

    rotateCCW: function() {
        var self = this;
        self.rotation = (self.rotation + 270) % 360;
        console.log(self.rotation);
        self.clearRotation();
        self.applyRotation(self.rotation);
    },

    toggleDoublePage: function() {
        var self = this;        
        self.doublePage = !self.doublePage;
        $("#comicImages").removeClass('spread1');
        $("#comicImages").removeClass('spread2');
        $("#comicImages").addClass(self.doublePage ? "spread2" : "spread1");
        self.drawPanel(self.currentPage, self.doublePage);
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

    handleClick: function(event){
        $('.tap-target').each(function(index){
            var top = $(this).offset().top - $(document).scrollTop();
            var left = $(this).offset().left;
            var height = $(this).height();
            var width = $(this).width();
            if(event.pageY >= top
                && event.pageY <= top+height
                && event.pageX >= left
                && event.pageX <= left+width
            ){
                $(this).trigger("toggle");
            }
        });
    },

    loadPages : function(){
    	if(model.numPages>0){
    		for(var i = 1; i<= comicJson.numPages; i++){
    		    images.push({loaded:true, path: "/page/"+comicJson.id+"/"+i+"/"});
    		}
    	}
    }

});
