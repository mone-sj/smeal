let index = {
  init: function () {
    console.log("hi");
    $("#main-group").on("change", () => {
      this.mainChange();
    });
    $("#btn-search").on("click", () => {
      this.searchIngredient();
    });
    $("#btn-searchFood").on("click", () => {
      this.searchFood();
    });
  },

  mainChange: function () {
    let groupId = $("#main-group option:selected").val();
    let target = document.getElementById("sub-group");
    target.options.length = 0;

    if (groupId == "default") {
      let opt = document.createElement("option");
      opt.value = "default";
      opt.text = "중분류";
      opt.innerHTML = "중분류";
      target.appendChild(opt);
    } else {
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
        })
        .fail(function (error) {
          alert(JSON.stringify(error));
        });
    }
  },
  searchIngredient: function () {
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
    })
      .done(function (fragment) {
        $("#resultDiv").replaceWith(fragment);
      })
      .fail(function (error) {
        alert(JSON.stringify(error));
      });
  },

  searchFood: function () {
    let searchFood = $("#foodName").val();
    console.log("searchFood: " + searchFood);

    $.ajax({
      type: "GET",
      url: "/auth/nBlogRecipe/" + searchFood,
    })
      .done(function (fragment) {
        $("#resultDiv").replaceWith(fragment);
      })
      .fail(function (error) {
        alert(JSON.stringify(error));
      });
  },
};

index.init();
