<button onclick="popupKakaoLogin()">KakaoLogin</button>
<button onclick="popupGoogleLogin()">GoogleLogin</button>
<button onclick="popupNaverLogin()">NaverLogin</button>
<script>
    function popupKakaoLogin() {
        window.open('${loginUrl1}', 'popupKakaoLogin', 'width=730,height=800,scrollbars=0,toolbar=0,menubar=no')
    }
    function popupGoogleLogin() {
        window.open('${loginUrl2}', 'popupGoogleLogin', 'width=730,height=800,scrollbars=0,toolbar=0,menubar=no')
    }
    function popupNaverLogin() {
        window.open('${loginUrl3}', 'popupNaverLogin', 'width=730,height=800,scrollbars=0,toolbar=0,menubar=no')
    }
</script>