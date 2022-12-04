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
import PostInterest from "../components/post/PostInteredt"
import HeaderLogged from "../components/header/HeaderLogged";
import React, { useEffect } from 'react';

import FullCalendar from "@fullcalendar/react";
// The import order DOES MATTER here. If you change it, you'll get an error!
import interactionPlugin from "@fullcalendar/interaction";
import timeGridPlugin from "@fullcalendar/timegrid";
import { useCookies } from 'react-cookie';

const HomeOne = ({ allPosts }) => {
    const [cookies, setCookie] = useCookies(['username'])
    console.log(cookies.username);
    useEffect(() => {
        if(cookies.username ==undefined){
            window.location.href = '/';
        }
    });
    return (
        <>
            <HeadMeta metaTitle="Home One" />
          
            {cookies.username != "undefined" && (
                <HeaderLogged />
            )}
            <PostSectionOne postData={allPosts} />
            {/* {console.log(allPosts)} */}
            <PostSectionTwo postData={allPosts} />
            <PostSectionThree postData={allPosts} />
            <PostInterest postData={allPosts}/>
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

