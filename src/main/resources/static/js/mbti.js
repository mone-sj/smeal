let index = {
    init: function(){

        $("#btn-submit").on("click", ()=>{
            this.submit();
        });
    },

    submit: function(){
        var result = {};
        // 질문지 길이 받아와서 +1하기
        for (var i=1; i<41; i++){
            var qNo='Q'+i;
            var inputData="input[name="+qNo+"]:checked";
            result[qNo]=$(inputData).val();
        }
        var gender = $('input[name=gender]:checked').val();
        var age = $('input[name=age]:checked').val();



        $.ajax({
            // 설문지 제출 요청
            type:"POST",
            timeout : 30000,
            url: "http://localhost:5000/data",
//            url: "http://localhost:5000/exam",
            data: result,
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            dataType: "json",
        }).done(function(resp){
            alert("제출이 완료되었습니다.");

            console.log(resp['success'][40]);
            var result_mbti = resp['success']
            result_mbti += "," + gender + "," + age;
            /*location.href = "/mbti/result"*/

            location.href = `/mbti/result/${result_mbti}`;
        }).fail(function(error){
            alert(JSON.stringify(error));
        });

    }

};

index.init();