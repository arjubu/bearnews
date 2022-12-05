import WidgetInstagram from "../../widget/WidgetInstagram";
import WidgetPost from "../../widget/WidgetPost";
import Notification from "../Notification";
import Link from "next/link";

import WidgetSocialShare from "../../widget/WidgetSocialShare";
import MetaDataOne from "./elements/meta/MetaDataOne";
import PostAuthor from "./elements/PostAuthor";
import PostComment from "./elements/PostComment";
import Comments from "./elements/Comments";
import {NotificationContainer, NotificationManager} from 'react-notifications';
import SocialShareBottom from "./elements/SocialShareBottom";
import SocialShareSide from "./elements/SocialShareSide";
import { useState } from "react";
import { Cookies } from "next/dist/server/web/spec-extension/cookies";


const PostFormatStandard = ({postData, allData, slug}) => {
  // console.log("postData");
  // console.log(postData);
  const [iflink,setiflink] = useState("");
  const renderErrorMessage = (name) =>
  name === iflink && (
    <div><a>Need more detail?          </a>
                        <Link href={postData.detaillink} >
                        <a>
                           Click me
                        </a>
                        </Link>
                        </div>
  );

  if(postData.detaillink!=null){
    setiflink("ID");
  }

  const basePathLink = process.env.NODE_ENV === 'production' ? process.env.NEXT_PUBLIC_BASEPATH ?? "" : "";
  
  const postContent = postData.content.replaceAll('/images/', basePathLink + '/images/');

    return (
      <>
        <MetaDataOne metaData={postData} />
        <div className="post-single-wrapper p-t-xs-60">
          <div className="container">
            <div className="row">
              <div className="col-lg-8">
                <main className="site-main">
                  <article className="post-details">
                    <div className="single-blog-wrapper">
                      <div dangerouslySetInnerHTML={{__html: postContent}}></div>
                      {renderErrorMessage("ID")}
                    </div>
                  </article>
				  <SocialShareBottom slug={slug}/>
				  <hr className="m-t-xs-50 m-b-xs-60" />
				  <PostAuthor authorData={postData}/>
				  <PostComment slug = {slug}/>
          <Comments slug = {slug}/>
         
                </main>
              </div>
              <div className="col-lg-4">
                <div className="post-sidebar">
                  <WidgetPost dataPost={allData} />
                  {/* <WidgetInstagram /> */}
                </div>
              </div>
            </div>
          </div>
        </div>
      </>
    );
}
 
export default PostFormatStandard;