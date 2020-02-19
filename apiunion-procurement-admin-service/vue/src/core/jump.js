

let apiJump = jumpData=>{
    // http://confluence.hzchengzi.com/pages/viewpage.action?pageId=27726409
    let href = '';
    switch(jumpData.jumpType){
        case 24: 
        href = !~jumpData.jumpData.indexOf('http://') ? `http://${jumpData.jumpData}` : jumpData.jumpData;
        break;
        case 26: 
        
        break;
        
    }
    return href;
}

export default apiJump;