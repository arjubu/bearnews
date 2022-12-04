import SectionTitle from "../elements/SectionTitle";
import PostLayoutTwo from "./layout/PostLayoutTwo";

const PostInterest = ({ postData }) => {
//console.log(postData);
const trendingPost = postData.filter(post => post.articleType === "BAYLORNEWS");

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
