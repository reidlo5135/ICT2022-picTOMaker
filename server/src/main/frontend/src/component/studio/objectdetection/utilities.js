export const drawRect = (detections, ctx) => {

    if (detections.length === 0 || detections[0]['class'] === "person") {
        const text = "인식되는 대상이 없습니다."

        ctx.font = '18px Ar ial'
        ctx.fillStyle = 'green';

        ctx.beginPath();
        ctx.fillText(text,320,240);
        ctx.stroke();
        return;
    }

    if (detections.length > 1) {
        const text = "인식 대상이 너무 많습니다. 한 가지 사물만 인식해주세요."

        ctx.font = '18px Arial'
        ctx.fillStyle = 'green';

        ctx.beginPath();
        ctx.fillText(text,320,240);
        ctx.stroke();
        return;
    }

    detections.forEach(prediction => {
        let text = prediction['class'];
        const color = 'green';
        ctx.strokeSylt = color;
        ctx.font = '18px Arial'
        ctx.fillStyle = color;
        
        ctx.beginPath();
        ctx.fillText(text,320,240);
        ctx.stroke();
    })
}