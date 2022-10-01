// Set new default font family and font color to mimic Bootstrap's default styling
(Chart.defaults.global.defaultFontFamily = "Nunito"),
  '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = "#858796";

$(document).ready(function () {
  initFunction();
});

function initFunction() {
  let data = {
    keyword: [{ param: "김치찌개" }, { param: "된장찌개" }],
  };

  $.ajax({
    type: "POST",
    url: "/auth/nShoppingKeywordTrendStatistics",
    // url: "/auth/nShoppingKeywordTrend",
    // data: JSON.stringify(data),
    // contentType: "application/json; charset=utf-8",
    dataType: "json",
  })
    .done(function (resp) {
      console.log("totalClickResult");
      console.log(resp.data.totalClick);
      let totalClickKeywordList = resp.data.totalClick["keywordList"];
      let totalClickKeywordResultList = resp.data.totalClick["ratioList"];

      // 클릭 추이 그래프
      let totalBarData = {
        labels: totalClickKeywordList,
        datasets: [
          {
            label: "클릭 추이량",
            backgroundColor: "#4e73df",
            hoverBackgroundColor: "#2e59d9",
            borderColor: "#4e73df",
            data: totalClickKeywordResultList,
            maxBarThickness: 25,
          },
        ],
      };

      var ctx = document.getElementById("totalChart");
      var totalBarChart = new Chart(ctx, {
        type: "bar",
        data: totalBarData,
        options: {
          maintainAspectRatio: false,
          layout: {
            padding: {
              left: 10,
              right: 25,
              top: 25,
              bottom: 0,
            },
          },
          scales: {
            xAxes: [
              {
                gridLines: {
                  display: false,
                  drawBorder: false,
                },
                ticks: {
                  maxTicksLimit: 3,
                },
              },
            ],
            yAxes: [
              {
                ticks: {
                  min: 0,
                  max: 100,
                  maxTicksLimit: 8,
                  padding: 10,
                },
                gridLines: {
                  color: "rgb(234, 236, 244)",
                  zeroLineColor: "rgb(234, 236, 244)",
                  drawBorder: false,
                  borderDash: [2],
                  zeroLineBorderDash: [2],
                },
              },
            ],
          },
          legend: {
            display: true,
          },
          tooltips: {
            titleMarginBottom: 10,
            titleFontColor: "#6e707e",
            titleFontSize: 14,
            backgroundColor: "rgb(255,255,255)",
            bodyFontColor: "#858796",
            borderColor: "#dddfeb",
            borderWidth: 1,
            xPadding: 15,
            yPadding: 15,
            displayColors: false,
            caretPadding: 10,
          },
        },
      });

      //연령별 추이 시작
      console.log("agesResult");
      console.log(resp.data.ages);

      let agesKeywordList = resp.data.ages["keyword"];
      let agesKeywordResultList = resp.data.ages["results"];

      console.log("agesKeywordList");
      console.log(agesKeywordList);
      console.log("agesKeywordResultList");
      console.log(agesKeywordResultList);

      for (agesKey in agesKeywordResultList) {
        window["ageData" + agesKey] = agesKeywordResultList[agesKey];
      }

      let age10 = {
        label: "10대",
        data: ageData10,
        backgroundColor: "#E63D17",
        hoverBackgroundColor: "#2e59d9",
        borderColor: "#E63D17",
        borderWidth: 1,
      };

      let age20 = {
        label: "20대",
        data: ageData20,
        backgroundColor: "#18C5F5",
        hoverBackgroundColor: "#2e59d9",
        borderColor: "#18C5F5",
        borderWidth: 1,
      };

      let age30 = {
        label: "30대",
        data: ageData30,
        backgroundColor: "#0D8DFC",
        hoverBackgroundColor: "#2e59d9",
        borderColor: "#0D8DFC",
        borderWidth: 1,
      };

      let age40 = {
        label: "40대",
        data: ageData40,
        backgroundColor: "#95E617",
        hoverBackgroundColor: "#2e59d9",
        borderColor: "#95E617",
        borderWidth: 1,
      };

      let age50 = {
        label: "50대",
        data: ageData50,
        backgroundColor: "#E67400",
        hoverBackgroundColor: "#2e59d9",
        borderColor: "#E67400",
        borderWidth: 1,
      };

      let age60 = {
        label: "60대",
        data: ageData60,
        backgroundColor: "#D4E628",
        hoverBackgroundColor: "#2e59d9",
        borderColor: "#D4E628",
        borderWidth: 1,
      };

      let agesBarData = {
        labels: agesKeywordList,
        datasets: [age10, age20, age30, age40, age50, age60],
      };

      // 연령별 그래프
      var ctx = document.getElementById("agesChart");
      var agesBarChart = new Chart(ctx, {
        type: "bar",
        data: agesBarData,
        options: {
          maintainAspectRatio: false,
          layout: {
            padding: {
              left: 10,
              right: 25,
              top: 25,
              bottom: 0,
            },
          },
          scales: {
            xAxes: [
              {
                stacked: false,
                time: {
                  unit: "month",
                },
                gridLines: {
                  display: false,
                  drawBorder: false,
                },
                ticks: {
                  maxTicksLimit: 6,
                },
              },
            ],
            yAxes: [
              {
                stacked: false,
                ticks: {
                  min: 0,
                  max: 100,
                  maxTicksLimit: 5,
                  padding: 10,
                },
                gridLines: {
                  color: "rgb(234, 236, 244)",
                  zeroLineColor: "rgb(234, 236, 244)",
                  drawBorder: false,
                  borderDash: [2],
                  zeroLineBorderDash: [2],
                },
              },
            ],
          },
          legend: {
            display: true,
          },
          tooltips: {
            titleMarginBottom: 10,
            titleFontColor: "#6e707e",
            titleFontSize: 14,
            backgroundColor: "rgb(255,255,255)",
            bodyFontColor: "#858796",
            borderColor: "#dddfeb",
            borderWidth: 1,
            xPadding: 15,
            yPadding: 15,
            displayColors: false,
            caretPadding: 10,
          },
        },
      });
      // 연령별 추이 - 끝

      // 성별 추이 그래프
      console.log("gendersResult");
      console.log(resp.data.genders);

      let gendersKeywordList = resp.data.genders["keyword"];
      let gendersKeywordResultList = resp.data.genders["results"];

      console.log("gendersKeywordList");
      console.log(gendersKeywordList);
      console.log("gendersKeywordResultList");
      console.log(gendersKeywordResultList);

      for (gendersKey in gendersKeywordResultList) {
        window["gendersKey" + gendersKey] =
          gendersKeywordResultList[gendersKey];
      }

      let female = {
        label: "여성",
        data: gendersKeyf,
        backgroundColor: "#F03F28",
        hoverBackgroundColor: "#F0503F",
        borderColor: "#F03F28",
        borderWidth: 1,
      };

      let male = {
        label: "남성",
        data: gendersKeym,
        backgroundColor: "#4e73df",
        hoverBackgroundColor: "#2e59d9",
        borderColor: "#4e73df",
        borderWidth: 1,
      };

      let gendersBarData = {
        labels: gendersKeywordList,
        datasets: [male, female],
      };

      // 성별 그래프
      var ctx = document.getElementById("gendersChart");
      var gendersBarChart = new Chart(ctx, {
        type: "bar",
        data: gendersBarData,
        options: {
          maintainAspectRatio: false,
          layout: {
            padding: {
              left: 10,
              right: 25,
              top: 25,
              bottom: 0,
            },
          },
          scales: {
            xAxes: [
              {
                stacked: false,
                time: {
                  unit: "month",
                },
                gridLines: {
                  display: false,
                  drawBorder: false,
                },
                ticks: {
                  maxTicksLimit: 6,
                },
              },
            ],
            yAxes: [
              {
                stacked: false,
                ticks: {
                  min: 0,
                  max: 100,
                  maxTicksLimit: 5,
                  padding: 10,
                },
                gridLines: {
                  color: "rgb(234, 236, 244)",
                  zeroLineColor: "rgb(234, 236, 244)",
                  drawBorder: false,
                  borderDash: [2],
                  zeroLineBorderDash: [2],
                },
              },
            ],
          },
          legend: {
            display: true,
          },
          tooltips: {
            titleMarginBottom: 10,
            titleFontColor: "#6e707e",
            titleFontSize: 14,
            backgroundColor: "rgb(255,255,255)",
            bodyFontColor: "#858796",
            borderColor: "#dddfeb",
            borderWidth: 1,
            xPadding: 15,
            yPadding: 15,
            displayColors: false,
            caretPadding: 10,
          },
        },
      });
    })
    .fail(function (error) {
      console.log(JSON.stringify(error));
    });
}
