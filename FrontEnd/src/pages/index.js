import { getAllPosts } from "../../lib/api";
import HeadMeta from "../components/elements/HeadMeta";
import FooterOne from "../components/footer/FooterOne";
import HeaderOne from "../components/header/HeaderOne";
import PostSectionFive from "../components/post/PostSectionFive";
import PostSectionFour from "../components/post/PostSectionFour";
import PostSectionOne from "../components/post/PostSectionOne";
import PostSectionSix from "../components/post/PostSectionSix";
import PostSectionThree from "../components/post/PostSectionThree";
import PostSectionTwo from "../components/post/PostSectionTwo";

import FullCalendar from "@fullcalendar/react";
// The import order DOES MATTER here. If you change it, you'll get an error!
import interactionPlugin from "@fullcalendar/interaction";
import timeGridPlugin from "@fullcalendar/timegrid";

const HomeOne = ({allPosts}) => {
 
  return ( 
    <>
    <HeadMeta metaTitle="Home One"/>
    <HeaderOne />
    <PostSectionOne postData={allPosts} />
    <PostSectionTwo postData={allPosts} />
    <PostSectionThree postData={allPosts} />
    <PostSectionFour postData={allPosts} />

    {/* <PostSectionSix postData={allPosts}/> */}
    <FooterOne />
    </>
   );
}
 
export default HomeOne;


export async function getStaticProps() {
  const allPosts = getAllPosts([
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

