(function() {
  var $;

  $ = jQuery;

  $.bootstrapGrowl = function(message, options) {
    var $alert, css, offsetAmount;

    options = $.extend({}, $.bootstrapGrowl.default_options, options);
    $alert = $("<div>");
    $alert.attr("class", "bootstrap-growl alert");
    if (options.type) {
      $alert.addClass("alert-" + options.type);
    }
    if (options.allow_dismiss) {
      $alert.append("<span class=\"close\" data-dismiss=\"alert\">&times;</span>");
    }
    $alert.append(message.escapeHTML());
    if (options.top_offset) {
      options.offset = {
        from: "top",
        amount: options.top_offset
      };
    }
    offsetAmount = options.offset.amount;
/*    $(".bootstrap-growl").each(function() {
      return offsetAmount = Math.max(offsetAmount, parseInt($(this).css(options.offset.from)) + $(this).outerHeight() + options.stackup_spacing);
    });*/
/*    css = {
      "position": (options.ele === "body" ? "fixed" : "absolute"),
      "margin": 0,
      "z-index": "9999",
      "display": "none"
    };
    css[options.offset.from] = offsetAmount + "px";*/
    css={
      "margin-bottom": 10,
      "display": "none"
    }
    $alert.css(css);
    if (options.width !== "auto") {
      $alert.css("width", options.width + "px");
    }
    if(options.ele == "body"){
        options.ele = ".s-growl";
      }
    if($(options.ele).length==0){
      if(options.ele == "body"){
        options.ele == ".s-growl";
        $('body').append('<div class = "s-growl"></div>');
      }else{
        var str = options.ele.substring(1);
        $('body').append('<div class = "'+str+'"></div>');
      }
      var _css={
        'position':'fixed',
      }
      _css[options.offset.from] = offsetAmount + 'px';
      $(options.ele).css(_css);
      switch (options.align) {
        case "center":
          $(options.ele).css({
            "left": "50%",
            "margin-left": "-" + ($alert.outerWidth() / 2) + "px"
          });
          break;
        case "left":
          $(options.ele).css("left", "20px");
          break;
        default:
          $(options.ele).css("right", "20px");
      }
    }
    $(options.ele).append($alert);
    $alert.fadeIn();
    if (options.delay > 0) {
      $alert.delay(options.delay).fadeOut(function() {
        return $(this).alert("close");
      });
    }
    return $alert;
  };

  $.bootstrapGrowl.default_options = {
    ele: "body",
    type: "info",
    offset: {
      from: "top",
      amount: 20
    },
    align: "right",
    width: 250,
    delay: 4000,
    allow_dismiss: true,
    stackup_spacing: 10
  };

}).call(this);