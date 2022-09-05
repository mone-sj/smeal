let index = {
    init: function(){

        $("#btn-submit").on("click", ()=>{
            this.submit();
        });
    },

    submit: function(){
        var result = {};
        result['Q1'] = $('input[name="Q1"]:checked').val()
        result['Q2'] = $('input[name="Q2"]:checked').val()
        result['Q3'] = $('input[name="Q3"]:checked').val()
        result['Q4'] = $('input[name="Q4"]:checked').val()
        result['Q5'] = $('input[name="Q5"]:checked').val()
        result['Q6'] = $('input[name="Q6"]:checked').val()
        result['Q7'] = $('input[name="Q7"]:checked').val()
        result['Q8'] = $('input[name="Q8"]:checked').val()
        result['Q9'] = $('input[name="Q9"]:checked').val()
        result['Q10'] = $('input[name="Q10"]:checked').val()
        result['Q11'] = $('input[name="Q11"]:checked').val()
        result['Q12'] = $('input[name="Q12"]:checked').val()
        result['Q13'] = $('input[name="Q13"]:checked').val()
        result['Q14'] = $('input[name="Q14"]:checked').val()
        result['Q15'] = $('input[name="Q15"]:checked').val()
        result['Q16'] = $('input[name="Q16"]:checked').val()
        result['Q17'] = $('input[name="Q17"]:checked').val()
        result['Q18'] = $('input[name="Q18"]:checked').val()
        result['Q19'] = $('input[name="Q19"]:checked').val()
        result['Q20'] = $('input[name="Q20"]:checked').val()
        result['Q21'] = $('input[name="Q21"]:checked').val()
        result['Q22'] = $('input[name="Q22"]:checked').val()
        result['Q23'] = $('input[name="Q23"]:checked').val()
        result['Q24'] = $('input[name="Q24"]:checked').val()
        result['Q25'] = $('input[name="Q25"]:checked').val()
        result['Q26'] = $('input[name="Q26"]:checked').val()
        result['Q27'] = $('input[name="Q27"]:checked').val()
        result['Q28'] = $('input[name="Q28"]:checked').val()
        result['Q29'] = $('input[name="Q29"]:checked').val()
        result['Q30'] = $('input[name="Q30"]:checked').val()
        result['Q31'] = $('input[name="Q31"]:checked').val()
        result['Q32'] = $('input[name="Q32"]:checked').val()
        result['Q33'] = $('input[name="Q33"]:checked').val()
        result['Q34'] = $('input[name="Q34"]:checked').val()
        result['Q35'] = $('input[name="Q35"]:checked').val()
        result['Q36'] = $('input[name="Q36"]:checked').val()
        result['Q37'] = $('input[name="Q37"]:checked').val()
        result['Q38'] = $('input[name="Q38"]:checked').val()
        result['Q39'] = $('input[name="Q39"]:checked').val()
        result['Q40'] = $('input[name="Q40"]:checked').val()
        console.log(result);


        result['data'] = 2;

        $.ajax({
            // 설문지 제출 요청
            type:"POST",
            timeout : 30000,
            /*url: "http://localhost:5000/data/mbti/",*/
            url: "http://localhost:5000/exam",
            /*data: JSON.stringify(data_sample),*/
            data: result,
            /*contentType: "application/json; charset=utf-8",*/
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            dataType: "json",
        }).done(function(resp){
            alert("제출이 완료되었습니다.");

            console.log(resp);
            /*location.href = "/mbti/result";*/
        }).fail(function(error){
            alert(JSON.stringify(error));
        });

    }

};

index.init();