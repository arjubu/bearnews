import FormGroup from "../../../contact/FormGroup";
import React, {Component} from "react";
import { useState } from "react";
import {
    MDBCol,
    MDBContainer,
    MDBRow,
    MDBCard,
    MDBCardText,
    MDBCardBody,
    MDBCardImage,
    MDBBtn,
    MDBBreadcrumb,
    MDBBreadcrumbItem,
    MDBProgress,
    MDBProgressBar,
    MDBIcon,
    MDBListGroup,
    MDBListGroupItem
  } from 'mdb-react-ui-kit';
import { useEffect } from "react";

  function DeleteComment(id) {
    fetch('http://137.184.37.205:8080/deleteComment?commentId='+id, {
        method: 'DELETE',
        headers: {
          "Content-type": "application/json; charset=UTF-8"
        }
      })
        .then(response => {
           
          if (response.status == 200) {
           // console.log('deleted');             
          } 
            
          });
}

class Comment extends Component {
    render () {
      return (
        <>
        <div className='comment' >
          <div className='comment-user'>
            <span>{this.props.comment.user} </span>：
          </div>
          <MDBRow>
          <MDBCol sm="9">
          <p>{this.props.comment.text}</p>
          </MDBCol>
          <MDBCol sm="2">
          <button className="deleteComment" onClick={()=>DeleteComment(this.props.comment.id)}>Delete</button>
          </MDBCol>
          <div className="border-bottom"></div>
          </MDBRow>
        </div>
        	  
              </>

      )
    }
  }
function CommentList({slug}) {
   
    const [comments,setComment] = useState([]);
    //   {username: 'Jerry', content: 'Hello'},
    //   {username: 'Tomy', content: 'World'},
    //   {username: 'Lucy', content: "window.location.href"}
    //console.log("slug for the comment reveiving: "+slug); 
    useEffect(() => {
    fetch('http://137.184.37.205:8080/fetchArticleAndComments/'+slug
  )
    .then(response => {
       
      if (response.status == 200) {
        //console.log('go'); 
        return response.json();
        
      } else {
        
        throw new Error('Something went wrong ...');

      }
        
      }).then(data=>{


        setComment(data.content.commentUsers);
        //console.log(Mylist);
      });
    });
    
      //console.log(comments);

      

    return(
      
  
        <div>
          {comments.map((comment, i) => <Comment comment={comment} key={i} />)}
        </div>
      
    );
  }

const Comments = ({slug}) => {

  return (
    <div className="comment-area">
      <div className="comments-box">
        {/* <p>
          Your email address will not be published.
          <span className="primary-color">*</span>
        </p> */}
      </div>
      {/* End of .comments-box */}
      <form action="#" className="comment-form row m-b-xs-60">
        <div className="col-12">
        <CommentList slug= {slug}/>      </div>
        {/* <div className="col-md-4">
			<FormGroup type="text" name="name" label="Name" />
        </div>
        <div className="col-md-4">
			<FormGroup type="text" name="email" label="Email" />
        </div> */}

      </form>
    </div>
  );
};

export default Comments;
