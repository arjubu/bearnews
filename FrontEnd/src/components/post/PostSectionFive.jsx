
import PostLayoutTwo from "./layout/PostLayoutTwo";

const PostSectionFive = ({postData, adBanner, pClass}) => {
    return ( 
        <div className={`random-posts ${pClass ?? "section-gap"}`}>
            <div className="container">
                <div className="row">
                    <div className="col-lg-8">
                        
                        <div className="axil-content">
                            {postData.slice(0, 8).map((data) => (
                                <PostLayoutTwo data={data} postSizeMd={true} key={data.slug}/>
                            ))}

                        </div>
                    </div>

                </div>
            </div>
        </div>

     );
}
 
export default PostSectionFive;