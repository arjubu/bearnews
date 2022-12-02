import FormGroup from "../../../contact/FormGroup";
import React, {Component} from "react";
class Comment extends Component {
    render () {
      return (
        <div className='comment' >
          <div className='comment-user'>
            <span>{this.props.comment.username} </span>：
          </div>
          <p>{this.props.comment.content}</p>
        </div>
      )
    }
  }
function CommentList() {
    const comments = [
        {username: 'Jerry', content: 'Hello'},
        {username: 'Tomy', content: 'World'},
        {username: 'Lucy', content: 'Good'}
      ]
    return(
      
  
        <div>
          {comments.map((comment, i) => <Comment comment={comment} key={i} />)}
        </div>
      
    );
  }

const Comments = () => {

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
        <CommentList></CommentList>        </div>
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
