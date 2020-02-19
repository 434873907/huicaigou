var showLoading = ()=> {
    var oldToast = document.getElementById('loading');
    if(oldToast) {
        return
    }
    var toast = document.createElement('div');
    toast.className = 'loading-start';
    var toastHolder = document.createElement('div');
    toastHolder.className = 'loading-box';
    toastHolder.appendChild(toast)
    var toastHolderBox = document.createElement('div');
    toastHolderBox.className = 'loading-big-box';
    toastHolderBox.id = 'loading';
    toastHolderBox.appendChild(toastHolder);
    document.body.appendChild(toastHolderBox);
};

var hideLoading = ()=> {
    var oldToast = document.getElementById('loading');
    if(oldToast) {
        document.body.removeChild(oldToast);
    }
};

var apiCount = 0;
var apiShowLoading = ()=>{
    if(apiCount <= 0) {
        showLoading();
    }
    apiCount++;
};

var apiHideLoading = ()=>{
    apiCount--;
    if(apiCount <= 0) {
        hideLoading();
    }
};

var apiClearLoading = ()=>{
    apiCount = 0;
    hideLoading();
};

export default {
    showLoading: showLoading,
    hideLoading: hideLoading,
    apiHideLoading: apiHideLoading,
    apiShowLoading: apiShowLoading,
    apiClearLoading: apiClearLoading,
}