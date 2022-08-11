import {useState,useHistory,useEffect} from "react";
import {Link} from "react-router-dom";
import axios from 'axios';
import '../../css/Callback.css';
import Top from "../contents/Top";

const CPosting = () => {
    const history = useHistory();
    const [isOpen, setIsOpen] = useState(false);
    const [profNickName, setProfNickName] = useState(null);
    const getProfile = localStorage.getItem('profile');
    const provider = localStorage.getItem('provider');

    const [inputValue, setInputValue] = useState({
        name: '',
        email: '',
        qna: ''
    });

    const { name, title ,content} = inputValue;

    const getProf = () => {
        try {
            const jsonProf = JSON.parse(getProfile);
            console.log('QNA jProf : ', jsonProf);
            setProfNickName(jsonProf.nickname);
        } catch (err) {
            console.error(err);
        }
    }

    useEffect(() => {
        getProf();
    }, []);

    const handleInput = e => {
        const { name, value } = e.target;
        setInputValue({
            ...inputValue,
            [name]: value,
        });
    };

    function onChangeIsOpen() {
        try {
            axios.post(`/v1/api/qna/register/${provider}`, {
                name: profNickName,
                content: content
            }).then((response) => {
                console.log('response : ', response.data);
                console.log('response : ', response.data.data);

                if(response.data.code === 0) {
                    alert('문의사항이 접수되었습니다.');
                    setIsOpen(!isOpen);
                    history.push("/");
                } else {
                    alert('An Error Occurred code : ' + response.data.code);
                }
            }).catch((err) => {
                console.error('err : ', JSON.stringify(err));
                alert(err.response.data.msg);
            });;
        } catch (err) {
            console.error(err);
        }
        
        /* setTimeout(() => {
            window.location.reload();
        }, 2500); */
    }

  return (
    <>
    <Top/>
          <div className="CommunityEditor">
              <div>
                  <span>작성자</span>
                  <input
                      name="title"
                      type="text"
                      value={profNickName}
                      onChange={handleInput}
                  />
                  <span>제목</span>
                  <input
                      name="title"
                      type="text"
                      placeholder="제목을 입력하세요"
                      onChange={handleInput}
                  />
                  <div>
                      <textarea
                          name="content"
                          type="text"
                          placeholder="내용을 입력하세요"
                          onChange={handleInput}
                      />
                  </div>
              </div>
              <div>
                  <button onClick={onChangeIsOpen}>저장하기</button>
              </div>
          </div>
    </>
  );
};
export default CPosting;