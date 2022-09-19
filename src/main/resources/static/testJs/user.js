let index = {
  init: function () {
    console.log("user.js 초기화됨");
    $("#btn-regist").on("click", () => {
      this.save();
    });
  },

  save: function () {
    console.log("save함수에 들어오김함.");
    let data = {
      username: $("#username").val(),
      nickname: $("#nickname").val(),
      email: $("#email").val(),
      password: $("#password").val(),
      passwordRepeat: $("#passwordRepeat").val(),
    };

    console.log("ajax 실행전임.");

    $.ajax({
      type: "POST",
      url: "/auth/joinProc",
      data: JSON.stringify(data),
      contentType: "application/json; charset=utf-8",
      dataType: "json",
    })
      .done(function (resp) {
        if (resp.status == 500) {
          alert("회원가입에 실패하였습니다.");
        } else {
          alert("회원가입이 완료되었습니다.");
          console.log(resp);
          location.href = "/";
        }
      })
      .fail(function (error) {
        alert(JSON.stringify(error));
      });
  },
};

index.init();
