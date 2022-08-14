import {useState,useEffect} from "react";
import {Link} from "react-router-dom";
import axios from 'axios';
import '../../css/Callback.css';
import Top from "../contents/Top";
import {useHistory} from "react-router-dom";
import upload from "../../image/studio_image/image_btn.png"
import "../../css/Community.css";

const CPosting = () => {
    const history = useHistory();
    const [isOpen, setIsOpen] = useState(false);
    const [profNickName, setProfNickName] = useState(null);
    const [email, setEmail] = useState(null);
    const getProfile = localStorage.getItem('profile');
    const provider = localStorage.getItem('provider');
    
    const [ img, setImg ] = useState([])
    const [ previewImg, setPreviewImg ] = useState([])


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
            setEmail(jsonProf.email);
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
            });
        } catch (err) {
            console.error(err);
        }
        
         setTimeout(() => {
            window.location.reload();
        }, 2500); 
    } 

    const insertImg = (e) => {
        let reader = new FileReader()

        if(e.target.files[0]) {
            reader.readAsDataURL(e.target.files[0])
            
            setImg([...img, e.target.files[0]])
        }

        reader.onloadend = () => {
            const previewImgUrl = reader.result

            if(previewImgUrl) {
            setPreviewImg([...previewImg, previewImgUrl])
            }
        }
      }

      const deleteImg = (index) => {
        const imgArr = img.filter((el, idx) => idx !== index)
        const imgNameArr = previewImg.filter((el, idx) => idx !== index )
      
        setImg([...imgArr])
        setPreviewImg([...imgNameArr])
      }

      const getPreviewImg = () => {
        if(img === null || img.length === 0) {
          return (
            <></>
          )
        } else {
          return img.map((el, index) => {
            const { name } = el
      
            return (
              <div key={index}>
                <div className="ImgArea">
                  <img src={previewImg[index]} />
                </div>
                <div className="ImgName">{name}</div>
                <button onClick={() => deleteImg(index)}>❌</button>
              </div>
              
            )
          })
        }
      }

      const registerBoard = () => {
          try {
              axios.post(`/v1/api/community/register/${provider}`, {
                  email,
                  title,
                  content
              }).then((response) => {
                  console.log('response : ', response.data);
                  console.log('response : ', response.data.data);

                  if(response.data.code === 0) {
                      alert('게시물이 등록되었습니다.');
                      setIsOpen(!isOpen);
                      history.push("/");
                  }
              }).catch((err) => {
                  console.error('err : ', JSON.stringify(err));
                  alert(err.response.data.msg);
              });
          } catch (err) {
              console.error(err);
          }
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
                  <form encType='multipart/form-data'>
                    <label htmlFor='file'><img className="uploadimg" src={upload} alt="사진 업로드 버튼"/></label>
                    <input className="upload" type="file" id='file' accept='image/jpg, image/jpeg, image/png' onChange={(e) => insertImg(e)}/>
                  </form>
                  <div>
                      <div 
                            className="postingarea"
                            contentEditable='true'
                          name="content"
                          type="text"
                          placeholder="내용을 입력하세요"
                           onChange={handleInput} 
                      >
                         {getPreviewImg()}
                        </div>
                        
                  </div>
              </div>
              <div>
                  <button onClick={() => {registerBoard();}}>저장하기</button>
              </div>
          </div>
    </>
  );
};
export default CPosting;