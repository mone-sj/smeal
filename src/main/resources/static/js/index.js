console.log('hi');
let check = 1;

function left() {
    console.log("left");
    check -= 1;
    if (check < 1) {
        check = 3;
    }
    change(check);
}

function right(){
    console.log("right");
    check += 1;
    if (check > 3){
        check =1;
    }
    change(check);

}

function change(e){
    const element = document.querySelector('#atag img');
    const atag = document.querySelector('#atag');
    console.log(atag.href);
    element.src = '/img/indexImg/'+'c'+e+'.png';
    if (e == 1){
        atag.href = '/mbti';
    }
    else if (e == 2){
        atag.href = '/auth/recipeSearchView';
    }
    else if (e == 3){
        atag.href = '/auth/board';
    }
}