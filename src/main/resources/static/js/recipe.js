let index = {
  init: function () {
    console.log("hi");
    $("#main-group").on("change", () => {
      this.mainChange();
    });
    $("#btn-search").on("click", () => {
      this.searchFood();
    });
  },

  mainChange: function () {
    let groupId = $("#main-group option:selected").val();
    let target = document.getElementById("sub-group");
    target.options.length = 0;

    $.ajax({
      type: "GET",
      url: "/auth/mainGroup/" + groupId,
      dataType: "json",
    })
      .done(function (resp) {
        let result = resp.data;
        for (key in result) {
          let opt = document.createElement("option");
          opt.value = result[key].foodName;
          opt.innerHTML = result[key].foodName;
          target.appendChild(opt);
        }
        //                alert("글쓰기가 완료되었습니다.");
        //                         location.href = "/auth/recipeSearchView";
      })
      .fail(function (error) {
        // 응답 실패하면 수행
        alert(JSON.stringify(error));
      });
  },
  searchFood: function () {
    let data = {
      rcp_parts_dtls: $("#sub-group").val(),
    };
    searchName = $("#sub-group").val();
    console.log("searchName: " + searchName);

    $.ajax({
      type: "POST",
      url: "/auth/foodRecipeList",
      data: JSON.stringify(data),
      contentType: "application/json; charset=utf-8",
      //dataType: "json",
    })
      .done(function (fragment) {
        $("#resultDiv").replaceWith(fragment);
        //console.log(resp.data);
      })
      .fail(function (error) {
        alert(JSON.stringify(error));
      });
  },
};

index.init();

//function mainGroupSelect(e) {
//  console.log("바뀜");
//  let mainGroupId=e.value;
//  console.log("e.value: "+mainGroupId);
//}
