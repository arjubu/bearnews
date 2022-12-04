import React, { useState, useRef, useEffect } from "react";
import Link from "next/link";
import { useCookies } from 'react-cookie';
import SockJsClient from 'react-stomp';

import {
    MDBCol,
    MDBContainer,
    MDBRow,
    MDBCard,
    MDBCardText,
    MDBCardBody,
    MDBCardImage,
    MDBBtn,
    MDBBreadcrumb,
    MDBBreadcrumbItem,
    MDBProgress,
    MDBProgressBar,
    MDBIcon,
    MDBListGroup,
    MDBListGroupItem
} from 'mdb-react-ui-kit';
import Image from "next/image";
import { dateFormate } from "../../utils";
import SocialLink from "../../data/social/SocialLink.json";
import MenuData from "../../data/menu/HeaderMenu.json";
import OffcanvasMenu from "./OffcanvasMenu";
import Select from 'react-select';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'

class Button extends React.Component {

    render() {
        const {
            variant,
            content,
            ...others
        } = this.props;

        return (
            <button className={variant} {...others}>
                {content}
            </button>
        )
    }
}

const HeaderLogged = () => {
    // Main Menu Toggle
    var menuRef = useRef();

    const toggleDropdownMenu = () => {
        const dropdownSelect = menuRef.current.childNodes;
        let dropdownList = [];

        for (let i = 0; i < dropdownSelect.length; i++) {
            const element = dropdownSelect[i];
            if (element.classList.contains("has-dropdown")) {
                dropdownList.push(element);
            }
        }

        dropdownList.forEach((element) => {
            element.children[0].addEventListener("click", () => {
                if (element.classList.contains("active")) {
                    element.classList.remove("active");
                    element.childNodes[1].classList.remove("opened");
                }
             
            });
        });
    };

    useEffect(() => {
        toggleDropdownMenu();
    }, []);

    // Offcanvas Menu
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    const [cookies, setCookie, removeCookie] = useCookies(['username']);

    const SOCKET_URL = 'http://localhost:8080/ws';
    const [message, setMessage] = useState(' ');
    const [bgClr, setBgClr] = useState("red");

    const onMessageReceived = (msg) => {
        setBgClr("yellow");
        console.log("--message recieved function--");
        setMessage(msg.message);
        console.log(message);
        console.log(msg)
        
        window.location.reload(false);
        alert(msg.message);
    }

    const handlerSearchChange = (e) => {
        setsearchValue(e);
        window.location.href = "http://localhost:3000/category/" + e;
    };
    function logOutClick() {
        console.log("--logout button clicked---")
        removeCookie(['username']);
        console.log("--cookie value after logout--")
        console.log(cookies.username);
        window.location.href = "/";
    }


    function SearchResultList() {
        const [DATASET, setDataset] = useState();

        function handlerChange(input) {
            //setsearchValue(input);
            fetch('http://localhost:8080/getTagByLetter', {
                mode: 'cors',
                method: 'POST',
                body: JSON.stringify({
                    suggString: input,
                }),
                headers: {
                    "Content-type": "application/json",
                }
            }
            )
                .then(response => {

                    if (response.status == 200) {
                        //console.log('this works'); 
                        return response.json();

                    } else {

                        throw new Error('Something went wrong ...');

                    }

                }).then(data => {
                    let a = [];
                    console.log(data);
                    if (data.data == "doesn't exsist") {
                        setDataset(a);
                        return data;
                    }
                    data.data.forEach(myfunction)
                    function myfunction(item) {
                        let b = { label: item.toString() };
                        a.push(b);
                    }
                    setDataset(a);

                    return data;
                    //console.log(Mylist);
                });
            //console.log(DATASET);

        }

        return (
            //const resultItems = this.props.data;

            <div className="search-field">
                {console.log("DATASET")}
                {console.log(DATASET)}
                <Select
                    onInputChange={(event) => handlerChange(event)}
                    options={DATASET}
                    getOptionValue={(option) => option.label}
                    //inputValue={this.state.searchKey}
                    onChange={opt => handlerSearchChange(opt.label)}

                />
            </div>

        );
    }


    // Header Search
    const [searchshow, setSearchShow] = useState(false);

    const headerSearchShow = () => {
        setSearchShow(true);
    };
    const headerSearchClose = () => {
        setSearchShow(false);
    };

    const changew = () => {
        window.location.href = "/otp";
    };

    // const searchTag = () =>{
    //   href
    // };

    // Mobile Menu Toggle
    const [mobileToggle, setMobileToggle] = useState(false);
    const [searchValue, setsearchValue] = useState(false);

    const MobileMenuToggler = () => {
        setMobileToggle(!mobileToggle);
        const HtmlTag = document.querySelector("html");
        const menuSelect = document.querySelectorAll(".main-navigation li");

        if (HtmlTag.classList.contains("main-menu-opened")) {
            HtmlTag.classList.remove("main-menu-opened");
        } else {
            setTimeout(() => {
                HtmlTag.classList.add("main-menu-opened");
            }, 800);
        }

        menuSelect.forEach((element) => {
            element.addEventListener("click", function () {
                if (!element.classList.contains("has-dropdown")) {
                    HtmlTag.classList.remove("main-menu-opened");
                    setMobileToggle(false);
                }
            });
        });
    };

    return (
        <>
            <SockJsClient
                url={SOCKET_URL}
                topics={['/topic/newPost']}
                onConnect={console.log("Connected!")}
                onMessage={msg => onMessageReceived(msg)}
                debug={false}
            />
            <OffcanvasMenu ofcshow={show} ofcHandleClose={handleClose} />
            <header className="page-header">
                <div className="header-top bg-grey-dark-one">
                    <div className="container">
                        <div className="row align-items-center">
                            <div className="col-md">
                                <ul className="header-top-nav list-inline justify-content-center justify-content-md-start">
                                    <li className="current-date">{dateFormate()}</li>
                                </ul>
                            </div>
                            <div className="col-md-auto">
                                <ul className="ml-auto social-share header-top__social-share">
                                    <li>
                                        <a href={SocialLink.fb.url}>
                                            <i className={SocialLink.fb.icon} />
                                        </a>
                                    </li>
                                    <li>
                                        <a href={SocialLink.twitter.url}>
                                            <i className={SocialLink.twitter.icon} />
                                        </a>
                                    </li>
                                    <li>
                                        <a href={SocialLink.instagram.url}>
                                            <i className={SocialLink.instagram.icon} />
                                        </a>
                                    </li>
                                    <li>
                                        <a href={SocialLink.linked.url}>
                                            <i className={SocialLink.linked.icon} />
                                        </a>
                                    </li>
                                    <li>
                                        <Button
                                            variant="default"
                                            style={{background: bgClr}}
                                        >
                                        </Button>
                                    </li>
                                    <li>
                                        <Link href="/profile">
                                            <a>Profile</a>
                                        </Link>
                                    </li>
                                    <li>
                                        <Link href="#">
                                            <a onClick={() =>logOutClick()}>Logout</a>
                                        </Link>
                                    </li>
                                   
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <nav className="navbar bg-white">
                    <div className="container">
                        <div className="navbar-inner">
                            <div className="brand-logo-container">
                                <Link href="/">
                                    <a>
                                        <Image
                                            src="/images/Bear_Mark_1_Color_01.jpg"
                                            alt="brand-logo"
                                            width={50}
                                            height={40}
                                        />
                                    </a>
                                </Link>
                            </div>
                            <div className="main-nav-wrapper">
                                <ul className="main-navigation list-inline" ref={menuRef}>
                                    {MenuData.map((data, index) =>
                                        data.submenu ? (
                                            <li className="has-dropdown" key={index}>
                                                <Link href={data.path}>
                                                    <a>{data.label}</a>
                                                </Link>
                                                <ul className="submenu">
                                                    {data.submenu.map((data, index) => (
                                                        <li key={index}>
                                                            <Link href={data.subpath}>
                                                                <a>{data.sublabel}</a>
                                                            </Link>
                                                        </li>
                                                    ))}
                                                </ul>
                                            </li>
                                        ) : (
                                            <li key={index}>
                                                <Link href={data.path}>
                                                    <a>{data.label}</a>
                                                </Link>
                                            </li>
                                        )
                                    )}
                                </ul>
                            </div>
                            <div className="navbar-extra-features ml-auto">
                                <form
                                    action="#"
                                    className={`navbar-search ${searchshow ? "show-nav-search" : ""
                                        }`}
                                >
                                    <div className="search-field">
                                        <SearchResultList></SearchResultList>

                                        {/* <Link href={"/category/"+searchValue}>
                    <button className="navbar-search-btn" type="button"  >
                      <i className="fal fa-search" />
                    </button>
                    </Link> */}

                                    </div>

                                    <span
                                        className="navbar-search-close"
                                        onClick={headerSearchClose}
                                    >
                                        <i className="fal fa-times" />
                                    </span>

                                </form>

                                <button
                                    className="nav-search-field-toggler"
                                    onClick={headerSearchShow}
                                >
                                    <i className="far fa-search" />
                                </button>

                                {/* <button className="side-nav-toggler" onClick={handleShow}>
                  <span />
                  <span />
                  <span />
                </button> */}
                            </div>
                            <div
                                className={`main-nav-toggler d-block d-lg-none ${mobileToggle ? "expanded" : ""
                                    }`}
                            >
                                <div className="toggler-inner" onClick={MobileMenuToggler}>
                                    <span />
                                    <span />
                                    <span />
                                </div>
                            </div>
                        </div>
                    </div>
                </nav>
            </header>
        </>
    );
};

export default HeaderLogged;
