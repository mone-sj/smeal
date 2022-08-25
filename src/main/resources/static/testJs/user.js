let index = {
  init: function () {
    $("#btn-save").on("click", () => {
      this.save();
    });
    $("#btn-update").on("click", () => {
      this.update();
    });
  },

  save: function () {
    console.log("save 함수로 들어온거 맞음?");
    let data = {
      username: $("#username").val(),
      password: $("#password").val(),
      email: $("#email").val(),
      nickname: $("nickname").val(),
      passwordRepeat: $("passwordRepeat").val(),
    };

    $.ajax({
      type: "POST",
      url: "/auth/joinProc",
      data: JSON.stringify(data), // http body data
      contentType: "application/json; charset=utf-8",
      dataType: "json",
    })
      .done(function (resp) {
        console.log(resp);
        // 응답 성공하면 수행
        if (resp.status == 500) {
          alert("회원가입에 실패하였습니다.");
        } else {
          alert("회원가입이 완료되었습니다.");
          console.log(resp);
          location.href = "/";
        }
      })
      .fail(function (error) {
        // 응답 실패하면 수행
        alert(JSON.stringify(error));
      });
  },

  update: function () {
    let data = {
      id: $("#id").val(),
      username: $("#username").val(),
      password: $("#password").val(),
      email: $("#email").val(),
      nickname: $("nickname").val(),
    };

    $.ajax({
      type: "PUT",
      url: "/auth/updateProc",
      data: JSON.stringify(data), // http body data
      contentType: "application/json; charset=utf-8",
      dataType: "json",
    })
      .done(function (resp) {
        // 응답 성공하면 수행
        alert("회원수정이 완료되었습니다.");
        console.log(resp);
        location.href = "/";
      })
      .fail(function (error) {
        // 응답 실패하면 수행
        alert(JSON.stringify(error));
      });
  },

  userDelete: function (id) {
    $.ajax({
      type: "DELETE",
      url: `/auth/delete/${id}`,
      contentType: "application/json; charset=utf-8",
      dataType: "json",
    })
      .done(function (resp) {
        // 응답 성공하면 수행
        alert("탈퇴가 완료되었습니다.");
        location.href = "/";
      })
      .fail(function (error) {
        // 응답 실패하면 수행
        alert(JSON.stringify(error));
      });
  },
};

index.init();
