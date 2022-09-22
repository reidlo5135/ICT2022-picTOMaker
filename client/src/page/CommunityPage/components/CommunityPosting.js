import {useState,useEffect} from "react";
import {Link} from "react-router-dom";
import axios,{instance, fileInstance} from 'axios';
import '../../SocialLoginPage/socialUserCallback.css';
import Top from "../../../component/Top";
import {useHistory} from "react-router-dom";
import upload from "../../../assets/image/studio_image/image_btn.png"
import "../community.css";

const CommunityPosting = () => {
    const history = useHistory();
    const [isOpen, setIsOpen] = useState(false);
    const [profNickName, setProfNickName] = useState(null);
    const [email, setEmail] = useState(null);
    const getProfile = localStorage.getItem('profile');
    const provider = localStorage.getItem('provider');
    const [contents, setContents] = useState("");

    const [ img, setImg ] = useState([])
    const [ previewImg, setPreviewImg ] = useState([])

    const [imgBase64, setImgBase64] = useState([]);
    const [imgFile, setImgFile] = useState(null);

    const [inputValue, setInputValue] = useState({
        name: '',
        email: '',
        qna: ''
    });

    const { name, title ,content} = inputValue;

    const [imageSrc, setImageSrc] = useState('');

    const getProf = () => {
        try {
            const jsonProf = JSON.parse(getProfile);
            console.log('QNA jProf : ', jsonProf);
            if(provider === 'LOCAL') {
                setProfNickName(jsonProf.nickName);
            } else {
                setProfNickName(jsonProf.name);
            }
            setEmail(jsonProf.email);
        } catch (err) {
            console.error(err);
        }
    }

    useEffect(() => {
        getProf();
        console.log(`img: ${imageSrc}`)
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
                content
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
            const image = imageSrc.toDataURL("image/png").replace("image/png", "image/octet-stream");
            console.log('image : ', image);
              axios.post(`/v1/api/community/${provider}`, {
                  email,
                  title,
                  content,
                  image
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

      const handleChangeFile = (e) => {
        console.log(e.target.files);
        setImgFile(e.target.files);
        setImgBase64([]);
        for(let i=0 ; i<e.target.files.length ; i++) {
            if(e.target.files[i]) {
                let reader = new FileReader();
                reader.readAsDataURL(e.target.files[i]);
                reader.onloadend = () => {
                    const base64 = reader.result; // 비트맵 데이터 리턴, 이 데이터를 통해 파일 미리보기가 가능함
                    console.log(base64)
                    if(base64) {
                        let base64Sub = base64.toString()
                        setImgBase64(imgBase64 => [...imgBase64, base64Sub]);
                    }
                }
            }
        }
    }

    /* const WriteBoard = async () => {
        const fd = new FormData();
        for(let i=0 ; i<imgFile.length ; i++) {
            fd.append("file", imgFile[i]);
        }
        await fileInstance({
            method: 'post',
            url: `/v1/api/community/${provider}`,
            data: fd
        })
        .then((response) => {
            if(response.data) {
                console.log(response.data)
                readImages();
                setImgFile(null);
                setImgBase64([]);
                alert("업로드 완료!");
            }
        })
        .catch((error) => {
            console.log(error)
            alert("실패!");
        })
    }
 */
    const encodeFileToBase64 = (fileBlob) => {
        const reader = new FileReader();
        reader.readAsDataURL(fileBlob);
        return new Promise((resolve) => {
        reader.onload = () => {
            setImageSrc(reader.result);
             resolve();
        };       
        });
    };


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
                  <form>
                    <label htmlFor='file'><img className="uploadimg" src={upload} alt="사진 업로드 버튼"/></label>
                    {/* <input className="upload" type="file" id='file' accept='image/jpg, image/jpeg, image/png' onChange={(e) => insertImg(e)}/> */}
                    {/* <input name="img"
                            type="file"
                            id="customFile"
                            /> */}
                            <input type="file" onChange={(e) => {
                                encodeFileToBase64(e.target.files[0]);
                            }} />
                            <div className="preview">
                                {imageSrc && <img src={imageSrc} alt="preview-img" />}
                            </div>


                  </form>
                  <div>
                      {getPreviewImg()}
                      <input
                          className="postingarea"
                          contentEditable='true'
                          name="content"
                          type="text"
                          placeholder="내용을 입력하세요"
                          onChange={handleInput}
                      />
                  </div>
              </div>
              <div>
                  <button onClick={() => {registerBoard();}} /* onClick={WriteBoard} */ >저장하기</button>
              </div>
          </div>
    </>
  );
};
export default CommunityPosting;