import Image from "next/image";
import { getAllPosts, getPostBySlug } from "../../../lib/api";
import Breadcrumb from "../../components/common/Breadcrumb";
import HeadMeta from "../../components/elements/HeadMeta";
import FooterOne from "../../components/footer/FooterOne";
import HeaderLogged from "../../components/header/HeaderLogged";
import PostLayoutTwo from "../../components/post/layout/PostLayoutTwo";
import WidgetCategory from "../../components/widget/WidgetCategory";
import WidgetPost from "../../components/widget/WidgetPost";
import { slugify } from "../../utils";
import { useCookies } from 'react-cookie';

const PostAuthor = ({postData, allPosts}) => {
    const authorContent = postData[0];
    const [cookies, setCookie] = useCookies(['username'])
    console.log(cookies.username);
    return ( 
        <>
        <HeadMeta metaTitle={authorContent.author_name} />
        <HeaderLogged />
        <Breadcrumb aPage={authorContent.author_name} />
        <div className="banner banner__default bg-grey-light-three">
            <div className="container">
                <div className="row align-items-center">
                    <div className="col-lg-12">
                        <div className="author-details-block">
                            <div className="media post-block post-block__mid m-b-xs-0">
                                <a href="#" className="align-self-center">
                                <Image
                                    src={authorContent.author_img}
                                    alt={authorContent.author_name}
                                    width={210}
                                    height={210}
                                    className="m-r-xs-30"
                                    />
                                    <div className="grad-overlay__transparent overlay-over" />
                                </a>
                                <div className="media-body">
                                    <h2 className="h4 m-b-xs-15">{authorContent.author_name}</h2>
                                    <p className="hover-line"><a href="https://example.com">https//www.example.com</a></p>
                                    <p className="mid">{authorContent.author_bio}</p>
                                    <div className="post-metas">
                                        <ul className="list-inline">
                                            <li><a href="#"><i className="fal fa-user-edit" />Total Post ({postData.length})</a></li>
                                        </ul>
                                    </div>
                                    <div className="author-social-share">
                                        <ul className="social-share social-share__with-bg">
                                            {authorContent.author_social.map((data, index)=> (
                                                <li key={index}><a href={data.url}><i className={data.icon} /></a></li>
                                            ))}
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div className="random-posts section-gap">
            <div className="container">
                <div className="row">
                    <div className="col-lg-8">
                        <div className="axil-content">
                            <h2 className="h3 m-b-xs-40">Articles By This Author</h2>
                            {postData.map((data) => (
                                <PostLayoutTwo data={data} postSizeMd={true} key={data.slug}/>
                            ))}
                        </div>
                    </div>
                    <div className="col-lg-4">
                        <div className="post-sidebar">
                            <WidgetCategory cateData={allPosts} />
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
 
export default PostAuthor;

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
        'author_name',
        'author_img',
        'author_social',
        'author_bio'
    ]);

    const getAuthorData = allPosts.filter(post => slugify(post.author_name) === postParams);
    const postData = getAuthorData;

    return {
        props: {
            postData,
            allPosts
        }
    }
}

export async function getStaticPaths() {
    const posts = await getAllPosts(['author_name']);

    const paths = posts.map(post => ({
        params: {
            slug: slugify(post.author_name)
        }
    }))

    return {
        paths,
        fallback: false,
    }
}