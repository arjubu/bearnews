// import FormGroup from "../../../contact/FormGroup";
import React, { useEffect, useRef ,useState} from 'react';
import { useCookies } from 'react-cookie';


function PostComment ({slug}) {
  console.log("This is comment slug: " +  slug)
  const [cookies, setCookie] = useCookies(['username'])
  const [errorMessages, error_login] = useState('');

  const [comment, setComment] = useState("");
  let commentText = "";
  const renderErrorMessage = (name) =>
  name === "ID" && (
    <div className="error">{errorMessages.message}</div>
  );
  const inputElement = useRef();
  const FormGroup = ({pClass, label, type, name, rows }) => {



    const InputFocusUI = () => {
        const selectElm = inputElement.current;
        const parentElm = inputElement.current.parentElement;

        selectElm.addEventListener('focusin', (e) => {
            parentElm.classList.add("focused");
        })
        selectElm.addEventListener('focusout', (e) => {
            if (!selectElm.value) {
                parentElm.classList.remove("focused");
            }
        })
    }

    useEffect(() => {
        InputFocusUI();
    }, []);

    return ( 
        <div className={`form-group ${pClass}`}>
            {label ? <label>{label}</label> : ""}
            {type === "textarea" ? 
            <textarea type={type} name={name} ref={inputElement} rows={rows ?? 3} required />: 
            <input type={type} name={name} ref={inputElement} required/> 
            }
        </div>
    );
}

  async function postcomment(){
    console.log("commentEmail: "+ cookies.username);
    console.log("commentText: "+ inputElement.current.value);
      if(cookies.username == undefined || cookies.username == "undifine"){
        error_login({ name: 'ID', message: "You need to login first to create a comment" });
      }else{

    fetch('http://137.184.37.205:8080/insertComment?articleId='+slug, {
      method: 'POST',
      body: JSON.stringify({
        commentText: inputElement.current.value,
        commentEmail: cookies.username
      }),
      headers: {
        "Content-type": "application/json; charset=UTF-8"
      }
    })
      .then(response => {

        if (response.status == 201) {
          console.log('go');
          return response.json();

        } else {
          error_login({ name: "ID", message: "create comment is not successful"});
          throw new Error('Something went wrong ...' + response.status);

        }

        }).then(data=>{
        });}


  }


    
  return (
    <div className="post-comment-area">
      <div className="comment-box">
        <h2>Leave A Reply</h2>
        {/* <p>
          Your email address will not be published.
          <span className="primary-color">*</span>
        </p> */}
      </div>
      {/* End of .comment-box */}
      <form action="#" className="comment-form row m-b-xs-60">
        <div className="col-12">
          <FormGroup pClass="comment-message-field" label="Comment" type="textarea" name="comment-message" rows={6}  />
        </div>
        {/* <div className="col-md-4">
			<FormGroup type="text" name="name" label="Name" />
        </div>
        <div className="col-md-4">
			<FormGroup type="text" name="email" label="Email" />
        </div> */}

        <div className="col-12">
          <button className="btn btn-primary" onClick={postcomment}>POST COMMENT</button>
          {renderErrorMessage('ID')}
        </div>
        
      </form>
      
    </div>
  );
};

export default PostComment;
