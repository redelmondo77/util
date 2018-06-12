   // $(".nav li").click(function() {
   //    $(".nav li").removeClass('active');
   //    $(this).addClass('active');
   // });


$(document).ready(function() {
    $("#locales").change(function () {
        var selectedOption = $('#locales').val();
        if (selectedOption != ''){
            window.location.replace('international?lang=' + selectedOption);
        }
    });
});

