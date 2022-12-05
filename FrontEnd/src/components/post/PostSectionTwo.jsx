import SectionTitle from "../elements/SectionTitle";
import PostLayoutThree from "./layout/PostLayoutThree";
import PostLayoutTwo from "./layout/PostLayoutTwo";

const PostSectionTwo = ({ postData }) => {

  const storyPost = postData.filter(post => post.articleType === "TWITTER");
  console.log("This is for twitter"+storyPost);

  return (
    <div className="section-gap section-gap-top__with-text trending-stories">
      <div className="container">
        <SectionTitle title="Twitter News" />
      <div className="row">
        {storyPost.slice(0, 6).map((data) => (
          <div className="col-lg-6" key={data.slug}>
              <PostLayoutTwo data={data}/>
          </div>
        ))}
      </div>
      </div>
    </div>
  );

};

export default PostSectionTwo;
