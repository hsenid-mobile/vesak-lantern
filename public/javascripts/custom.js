

$(document).ready(function() {
    // check name availability on focus lost
    checkVoteNum();
    changeLanternColor()
});

var checkVoteNum =  function() {
    $.ajax({
        url: '/getVotes',
        success: function(data, textStatus, jqXHR) {

            var totalValues = JSON.parse(data);
            console.log(totalValues.vote.red);
            console.log(totalValues.percentage.red);

            $('#redVotesLabel').html(totalValues.vote.red);
            $('#greenVotesLabel').html(totalValues.vote.blue);
            $('#blueVotesLabel').html(totalValues.vote.green);

            $('#redProgressBar').width(totalValues.percentage.red+'%').text(totalValues.percentage.red+'%')
            $('#greenProgressBar').width(totalValues.percentage.blue+'%').text(totalValues.percentage.blue+'%')
            $('#blueProgressBar').width(totalValues.percentage.green+'%').text(totalValues.percentage.green+'%')

            setTimeout(checkVoteNum, 1000)
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log("Error occurred" + errorThrown)
        }

    });
}

//
//var changeLanternColor = function() {
//    $.ajax({
//        url: '/changeColor',
//        success: function(data, textStatus, jqXHR) {
////            rect1.style.fill=data
//
//            var dataSplit = data.split(':');
//            var red      = dataSplit[0];
//            var green  = dataSplit[1];
//            console.log('Red Val' + red);
//            console.log('Green Vlues' + green);
//            setTimeout(changeLanternColor, 3000)
//        },
//        error: function(jqXHR, textStatus, errorThrown) {
////            window.alert(textStatus);
//        }
////                setTimeout(checkPositionAvailability, 3000)
//
//    });
//}

var changeLanternColor = function() {
    $.ajax({
        url: '/getLanternColor',
        success: function(data, textStatus, jqXHR) {
            var colorValues = JSON.parse(data);
            console.log(colorValues.s1);
            redImg.style.opacity = colorValues.s1.red/255;
            greenImg.style.opacity = colorValues.s1.green/255;
            blueImg.style.opacity = colorValues.s1.blue/255;
            setTimeout(changeLanternColor, 1000)
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log("Error occurred" + errorThrown)
//            window.alert(textStatus);
        }
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


//jQuery(function ($) {
//    $('#redButton').click(function () {
////        var val = Math.floor((Math.random() * 100)) + '%';
//        $("#redButton").css("background-color", "blue");
////        $('#').css(background-image: url("../img/glyphicons-halflings-blue.png");})
//    })
//});
//
//
//jQuery(function ($) {
//    $('#greenButton').click(function () {
//        var val = Math.floor((Math.random() * 100)) + '%';
//        $('#greenProgressBar').width(val).text(val)
//        $('#full').style.backgroundColor = "#f47121";
//
//    })
//});

//var svgMod = function() {
//    var circle1 = document.getElementById("circle1");
//    circle1.style.fill="blue";
//}

