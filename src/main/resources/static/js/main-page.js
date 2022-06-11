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

    $("#csv").click(function(){
        console.log("******************")
        $('table').each(function(){
            console.log("===============")
            let $table = $(this);
            let csv = $table.table2CSV({delivery: 'value'});
            downloadCSVFile(csv)
        });
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
                console.log(page)
                console.log(response)
                page++
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
                    addRandomData(10)

            }
        }
    );

    function downloadCSVFile(csv_data) {
        let CSVFile = new Blob([csv_data], { type: "text/csv" });

        let temp_link = document.createElement('a');

        temp_link.download = "data.csv";
        temp_link.href = window.URL.createObjectURL(CSVFile);

        temp_link.style.display = "none";
        document.body.appendChild(temp_link);

        // Automatically click the link to trigger download
        temp_link.click();
        document.body.removeChild(temp_link);
    }

})