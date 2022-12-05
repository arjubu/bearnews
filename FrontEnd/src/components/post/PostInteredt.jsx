import { Cookies } from "next/dist/server/web/spec-extension/cookies";
import React, { useState , Component, useEffect} from "react";

import SectionTitle from "../elements/SectionTitle";
import PostLayoutTwo from "./layout/PostLayoutTwo";
import { useCookies } from 'react-cookie';

const PostInterest = ({ postData }) => {
//console.log(postData);
const [cookies, setCookie] = useCookies(['username'])
const [taglist,setTaglist] = useState([]);


useEffect(() => {
    
    fetch('http://localhost:8080/showUserInterest', {
        method: 'POST',
        body: JSON.stringify({
            useremail :cookies.username ,
        }),
        headers: {
          "Content-type": "application/json; charset=UTF-8"
        }
      })
        .then(response => {
           
          if (response.status == 200) {
           return response.json();
            
            
          } else if (response.status == 400) {

  
          }else{
            error_login({ name: "ID", message: "Send email fail"});
            throw new Error('Something went wrong ...');
  
          }
            
          }).then(data =>{
            setTaglist(data);
          });

  });
  


//taglist.contains(post.cate)
const trendingPost = postData.filter(post => taglist.includes(post.cate)  );

  return (
    <div className="section-gap section-gap-top__with-text trending-stories">
      <div className="container">
        <SectionTitle title="Recommondation" />
      <div className="row">
        {trendingPost.slice(0, 6).map((data) => (
          <div className="col-lg-6" key={data.slug}>
              <PostLayoutTwo data={data}/>
          </div>
        ))}
      </div>
      </div>
    </div>
  );
};

export default PostInterest;
