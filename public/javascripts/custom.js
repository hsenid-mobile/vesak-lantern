

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
//            console.log(totalValues.vote.red);
//            console.log(totalValues.percentage.red);

            $('#redVotesLabel').html(totalValues.vote.red);
            $('#greenVotesLabel').html(totalValues.vote.green);
            $('#blueVotesLabel').html(totalValues.vote.blue);

            $('#redProgressBar').width(totalValues.percentage.red+'%').text(totalValues.percentage.red+'%')
            $('#greenProgressBar').width(totalValues.percentage.green+'%').text(totalValues.percentage.green+'%')
            $('#blueProgressBar').width(totalValues.percentage.blue+'%').text(totalValues.percentage.blue+'%')

            setTimeout(checkVoteNum, 1000)
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log("Error occurred" + errorThrown)
        }

    });
}

var changeLanternColor = function() {
    $.ajax({
        url: '/getLanternColor',
        success: function(data, textStatus, jqXHR) {
            var colorValues = JSON.parse(data);
//            console.log(colorValues.s1);
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
