

$(document).ready(function() {
    // check name availability on focus lost
    checkAvailability();
    changeLanternColor();
    changOpacity()
});

var checkAvailability =  function() {
    $.ajax({
        url: '/getVotes',
        success: function(data, textStatus, jqXHR) {
            $('#ajaxMsg').html(data);
            $('#ajaxMsgBlue').html(data);
            $('#redProgressBar').width(data+'%').text(data+'%')
            $('#blueProgressBar').width(data+'%').text(data+'%')
            setTimeout(checkAvailability, 3000)
        },
        error: function(jqXHR, textStatus, errorThrown) {
            window.alert(textStatus);
        }

    });
}


var changeLanternColor = function() {
    $.ajax({
        url: '/changeColor',
        success: function(data, textStatus, jqXHR) {
            rect1.style.fill=data
            setTimeout(changeLanternColor, 3000)
        },
        error: function(jqXHR, textStatus, errorThrown) {
            window.alert(textStatus);
        }
//                setTimeout(checkPositionAvailability, 3000)

    });
}

var changOpacity = function() {
    $.ajax({
        url: '/getOpacity',
        success: function(data, textStatus, jqXHR) {
            console.log("values :"+ data);
            blueImg.style.opacity = data;
            setTimeout(changOpacity, 3000)
        },
        error: function(jqXHR, textStatus, errorThrown) {
            window.alert(textStatus);
        }
//                setTimeout(checkPositionAvailability, 3000)

    });
}


var redButtonOnclick = function () {
    $.ajax({
        url: '/redButtonOnclick',
        success: function(data, textStatus, jqXHR) {
        },
        error: function(jqXHR, textStatus, errorThrown) {
        }

    });
}

var greenButtonOnclick = function() {
    $.ajax({
        url: '/greenButtonOnclick',
        success: function(data, textStatus, jqXHR) {
        },
        error: function(jqXHR, textStatus, errorThrown) {
        }

    });
}

var blueButtonOnclick = function () {
    $.ajax({
        url: '/blueButtonOnclick',
        success: function(data, textStatus, jqXHR) {
        },
        error: function(jqXHR, textStatus, errorThrown) {
        }

    });
}

jQuery(function ($) {
    $('#redButton').click(function () {
        var val = Math.floor((Math.random() * 100)) + '%';
        $('#redProgressBar').width(val).text(val)
    })
});


jQuery(function ($) {
    $('#greenButton').click(function () {
        var val = Math.floor((Math.random() * 100)) + '%';
        $('#greenProgressBar').width(val).text(val)
        $('#full').style.backgroundColor = "#f47121";

    })
});

//var svgMod = function() {
//    var circle1 = document.getElementById("circle1");
//    circle1.style.fill="blue";
//}

