import { getAllPosts } from "../../../lib/api";
import FooterOne from "../../components/footer/FooterOne";
import HeaderOne from "../../components/header/HeaderOne";
import Breadcrumb from "../../components/common/Breadcrumb";
import { slugify } from "../../utils";
import HeadMeta from "../../components/elements/HeadMeta";
import WidgetSocialShare from "../../components/widget/WidgetSocialShare";
import WidgetPost from "../../components/widget/WidgetPost";
import PostLayoutTwo from "../../components/post/layout/PostLayoutTwo";
import WidgetCategory from "../../components/widget/WidgetCategory";
import HeaderLogged from "../../components/header/HeaderLogged";

const PostCategory = ({ postData, allPosts }) => {
    const cateContent = postData[0];

    return (
        <>
            <HeadMeta metaTitle={cateContent.cate}/>
            <HeaderLogged />
            <Breadcrumb aPage={cateContent.cate} />
            {/* Banner Start here  */}
            <div className="banner banner__default bg-grey-light-three">
                <div className="container">
                    <div className="row align-items-center">
                        <div className="col-lg-12">
                            <div className="post-title-wrapper">
                                <h2 className="m-b-xs-0 axil-post-title hover-line">{cateContent.cate}</h2>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            {/* Banner End here  */}
            <div className="random-posts section-gap">
                <div className="container">
                    <div className="row">
                        <div className="col-lg-8">
                            <div className="axil-content">
                                {postData.map((data) => (
                                    <PostLayoutTwo data={data} postSizeMd={true} key={data.slug}/>
                                ))}
                            </div>
                        </div>
                        <div className="col-lg-4">
                            <div className="post-sidebar">
                                <WidgetPost dataPost={allPosts} />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <FooterOne />
        </>
    );
}

export default PostCategory;


export async function getStaticProps({ params }) {

    const postParams = params.slug;

    const allPosts = await getAllPosts([
        'slug',
        'cate',
        'cate_img',
        'title',
        'excerpt',
        'featureImg',
        'date',
        'post_views',
        'read_time',
        'author_name',
        'author_social'
    ]);

    const getCategoryData = allPosts.filter(post => slugify(post.cate) === postParams);
    const postData = getCategoryData;

    return {
        props: {
            postData,
            allPosts
        }
    }
}

export async function getStaticPaths() {
    const posts = await getAllPosts(['cate']);

    const paths = posts.map(post => ({
        params: {
            slug: slugify(post.cate)
        }
    }))

    return {
        paths,
        fallback: false,
    }
}