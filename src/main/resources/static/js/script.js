console.log("script loaded");

// change theme working script starts
let currentTheme = getTheme();
console.log(currentTheme);

// initial theme set when first time window loads
document.addEventListener('DOMContentLoaded', ()=> {
    changeTheme();
})



// function to change the theme mode
function changeTheme() {
    // set to web page
    changePageTheme(currentTheme, currentTheme);

    // set the listner to change theme 
    const changeThemeButton =  document.querySelector("#theme_change_button");
    changeThemeButton.addEventListener("click", ()=> {
        console.log("change theme button clicked");
        
        let oldTheme = currentTheme;

        if (currentTheme==="light") 
            currentTheme = "dark";
        else
        currentTheme = "light"

        changePageTheme(currentTheme, oldTheme);
    })
}


// set theme in local storage
function setTheme(theme) {
    localStorage.setItem("theme", theme);
}


// get theme from local storage
function getTheme() {
    let theme = localStorage.getItem("theme");
    return theme ? theme : "light";
}


// apply the theme change on the page when button clicked
function changePageTheme(theme, oldTheme) {
    // update in local storage
    setTheme(currentTheme);

    // remove old theme
    document.querySelector("html").classList.remove(oldTheme);

    // apply the changed theme on page
    document.querySelector("html").classList.add(currentTheme);

    // change the mode text
    document.querySelector("#theme_change_button").querySelector("span").textContent = currentTheme === "light" ? "Dark" : "Light";
}
// change theme working script ends