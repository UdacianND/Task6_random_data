$(document).ready(function(){

    let page = 1;
    let index = 1;

    let table = $("#user-list")

    $("#submit").click(function(){
        page = 1
        index = 1
        table.html("")
        addRandomData(20)
    })

    function addRandomData(numberOfRows){
        let country = $("#country option:selected").val()
        let numberOfErrors = $("#error").val()
        let seed = $("#seed").val()

        let dataInfo = JSON.stringify({
            "country" : country,
            "errors" : parseFloat(numberOfErrors),
            "seed" : parseInt(seed),
            "page" : page,
            "rows" : parseInt(numberOfRows)
        })

        $.ajax({
            type: "POST",
            url: "api/random-data",
            data: dataInfo,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success:function (response){
                for (let i = 0; i < response.length; i++) {
                    let user = response[i]
                    let newUser = $('<tr></tr><td>'+index+'</td> <td>'+user.id+'</td>'+
                        '<td>'+user.fullName+'</td> <td>'+user.address+'</td> <td>'+user.phoneNumber+'</td></tr>')
                    table.append(newUser)
                    index++
                }

            }
        })
    }

    $(window).scroll(
        function() {
            let scrollTop = $(document.documentElement).scrollTop() || $(document.body).scrollTop();
            let offsetHeight = $(document.body).outerHeight();
            let clientHeight = document.documentElement.clientHeight;
            if (offsetHeight <= scrollTop + clientHeight) {
                page++
                addRandomData(10)
            }
        }
    );
})