
// 카메라 사용 가능 여부 boolean return
export function DeviceCheck() {
    return true;
    try {
        if (!navigator.mediaDevices || !navigator.mediaDevices.enumerateDevices) {
            console.log("지원하지 않는 환경입니다.")
            return;
        }
    
        if (!navigator.mediaDevices.getUserMedia) {
            navigator.mediaDevices.enumerateDevices().then((devices)=> {
                devices.forEach((device)=> {
                    // 인식된 디바이스에 관한 정보 출력
                    console.log(device.kind + ": " + device.label + " id = " + device.devicId);
                });
            });
        }
       
        return true;
    } catch (e) {
        console.log(e);
        return false;
    }
}

export function getStream() {
    return "디바이스 스트림"
    navigator.mediaDevices.getUserMedia({audio:false, video:true}).then((stream)=> {
        return stream;
    });
}