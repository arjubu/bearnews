import { getAllPosts } from "../../lib/api";
import HeadMeta from "../components/elements/HeadMeta";
import FooterOne from "../components/footer/FooterOne";
import HeaderOne from "../components/header/HeaderOne";
import PostSectionFive from "../components/post/PostSectionFive";
import PostSectionFour from "../components/post/PostSectionFour";
import PostSectionOne from "../components/post/PostSectionOne";
import PostSectionSeven from "../components/post/PostSectionSix";
import PostSectionThree from "../components/post/PostSectionThree";
import PostSectionTwo from "../components/post/PostSectionTwo";
import HeaderLogged from "../components/header/HeaderLogged";


import { useCookies } from 'react-cookie';

const HomeOne = ({allPosts}) => {
    const [cookies, setCookie] = useCookies(['username'])
    console.log(cookies.username);
    if(cookies.username !=undefined){
      window.location.href = '/userHome';
    }
  return ( 
    <>
          <HeadMeta metaTitle="Home One" />
          {cookies.username ==undefined  && (
              <HeaderOne/>
          )}
          {cookies.username != undefined && (
              <HeaderLogged />
          )}
          
    <PostSectionOne postData={allPosts} />
    {/* {console.log(allPosts)} */}
    <PostSectionTwo postData={allPosts} />
    <PostSectionThree postData={allPosts} />
    {/* <PostSectionFour postData={allPosts} /> */}

    {/* <PostSectionSix postData={allPosts}/> */}
    <FooterOne />
    </>
   );
}
 
export default HomeOne;


export async function getStaticProps() {
  const allPosts = await getAllPosts([
    'postFormat',
    'trending',
    'story',
    'slug',
    'title',
    'excerpt',
    'featureImg',
    'cate',
    'cate_bg',
    'cate_img',
    'author_name',
    'date'
  ])
  
  return {
    props: { allPosts }
  }
}

