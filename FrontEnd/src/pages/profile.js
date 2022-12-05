import React, { useState , Component, useEffect} from "react";
import Creatable from 'react-select/creatable';
import { EasyButton } from 'react-easy-button';
import { useCookies } from 'react-cookie';

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
  } from 'mdb-react-ui-kit';import FooterOne from "../components/footer/FooterOne";
import HeaderLogged from "../components/header/HeaderLogged";
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

export default function PersonalProfile() {
    const [name, setName] = useState("");
    const [respStatus, setrespStatus] = useState("");
    const [cookies, setCookie, removeCookie] = useCookies(['username']);
    console.log("--profile page cookie--");
    console.log(cookies.username);
    useEffect(() => {
        getProfileData();
    },[]);
  
    const getProfileData = async () => {
        const response = await fetch(
            "http://137.184.37.205:8080/displayUserProfile", {
            method: 'POST',
            body: JSON.stringify({
                username: cookies.username,
            }),
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }
        }
        ).then(response => {
            setrespStatus = response.status;
            console.log("--response status--");
            console.log(response.status);
            if (response.status==200)
            return response.json();
        })
            .then((response) => {
                console.log("--api calling status--");
                console.log('response', response);
                console.log("--response fields--");
                console.log(response.data.reqLastName);
                console.log(response.data.reqInterestList);
                      
                    })
        .then((data) => {
            console.log('---not using data after profile api calling---',);

        });
    };
    function SearchResultList (){
        const [DATASET, setDataset] = useState();
      
         function handlerChange(input){
             fetch('http://137.184.37.205:8080/getTagByLetter', {
            method: 'POST',
            body: JSON.stringify({
              suggString : input,
            }),
            headers: {
              "Content-type": "application/json; charset=UTF-8"
            }
          }
          )
            .then(response => {
               
              if (response.status == 200) {
                //console.log('this works'); 
                return response.json();
                
              } else {
                
               // throw new Error('Something went wrong ...');
        
              }
                
              }).then(data=>{
                let a = [];
                console.log(data);
                if(data.data == "doesn't exsist"){
                  setDataset(a);
                  return data;
                }
              data.data.forEach(myfunction)
              function myfunction(item){
                let b = {label:item.toString()};
                a.push(b);
              }
              setDataset(a);
              
                return data;
                //console.log(Mylist);
              });
              //console.log(DATASET);
      
        }
      
        return(
          //const resultItems = this.props.data;
       
            <div className="upload-tag">
              {console.log("DATASET")}
              {console.log(DATASET)}
              <Creatable
              isMulti
              onInputChange = {(event) => handlerChange(event) }
              options={DATASET}
              getOptionValue={(option) => option.label}
              //inputValue={this.state.searchKey}
              onChange={opt => (opt.label)}
              
              />
            </div>
          
        );
      }


  return (
    <>
    <HeaderLogged />

    <section style={{ backgroundColor: '#eee' }}>
      <MDBContainer className="py-5">
        <MDBRow>
          <MDBCol>
            <MDBBreadcrumb className="bg-light rounded-3 p-3 mb-4">
              <MDBBreadcrumbItem>
                <a href='/'>Home</a>
              </MDBBreadcrumbItem>
              <MDBBreadcrumbItem active>profile</MDBBreadcrumbItem>
            </MDBBreadcrumb>
          </MDBCol>
        </MDBRow>

        <MDBRow>
          <MDBCol lg="4">
            <MDBCard className="mb-4">
              <MDBCardBody className="text-center">
                <MDBCardImage
                  src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava3.webp"
                  alt="avatar"
                  className="rounded-circle"
                  style={{ width: '150px' }}
                  fluid />
              </MDBCardBody>
            </MDBCard>

            <MDBCard className="mb-4 mb-lg-0">
              <MDBCardBody className="p-0">
                <MDBListGroup flush className="rounded-3">

                  <MDBListGroupItem className="d-flex justify-content-between align-items-center p-3">
                    <MDBIcon fab icon="twitter fa-lg" style={{ color: '#55acee' }} />
                    <MDBCardText>@mdbootstrap</MDBCardText>
                  </MDBListGroupItem>
                  <MDBListGroupItem className="d-flex justify-content-between align-items-center p-3">
                    <MDBIcon fab icon="instagram fa-lg" style={{ color: '#ac2bac' }} />
                    <MDBCardText>mdbootstrap</MDBCardText>
                  </MDBListGroupItem>
                  <MDBListGroupItem className="d-flex justify-content-between align-items-center p-3">
                    <MDBIcon fab icon="facebook fa-lg" style={{ color: '#3b5998' }} />
                    <MDBCardText>mdbootstrap</MDBCardText>
                  </MDBListGroupItem>
                </MDBListGroup>
              </MDBCardBody>
            </MDBCard>
          </MDBCol>
          <MDBCol lg="8">
            <MDBCard className="mb-4">
              <MDBCardBody>
                <MDBRow>
                  <MDBCol sm="3">
                    <MDBCardText>Full Name</MDBCardText>
                  </MDBCol>
                  <MDBCol sm="9">
                    <MDBCardText className="text-muted">Johnatan Smith</MDBCardText>
                  </MDBCol>
                </MDBRow>
                <hr />
                <MDBRow>
                  <MDBCol sm="3">
                    <MDBCardText>Email</MDBCardText>
                  </MDBCol>
                  <MDBCol sm="9">
                    <MDBCardText className="text-muted">example@example.com</MDBCardText>
                  </MDBCol>
                </MDBRow>
                <hr />
                <MDBRow>
                  <MDBCol sm="3">
                    <MDBCardText>Phone</MDBCardText>
                  </MDBCol>
                  <MDBCol sm="9">
                    <MDBCardText className="text-muted">(097) 234-5678</MDBCardText>
                  </MDBCol>
                </MDBRow>
                <hr />
                <MDBRow>
                  <MDBCol sm="3">
                    <MDBCardText>Mobile</MDBCardText>
                  </MDBCol>
                  <MDBCol sm="9">
                    <MDBCardText className="text-muted">(098) 765-4321</MDBCardText>
                  </MDBCol>
                </MDBRow>
                <hr />
                <MDBRow>
                  <MDBCol sm="3">
                    <MDBCardText>Address</MDBCardText>
                  </MDBCol>
                  <MDBCol sm="9">
                    <MDBCardText className="text-muted">Bay Area, San Francisco, CA</MDBCardText>
                  </MDBCol>
                </MDBRow>
                <hr />
                <MDBRow>
                  <MDBCol sm="3">
                    <MDBCardText>My interest tags</MDBCardText>
                  </MDBCol>
                  <MDBCol sm="9">
                    <MDBCardText className="text-muted">tag,tag</MDBCardText>
                  </MDBCol>
                </MDBRow>
              </MDBCardBody>
            </MDBCard>
            <MDBRow>
            <MDBCol md="6">
            <SearchResultList></SearchResultList>
            </MDBCol>
            <MDBCol md="6">
            <Button className="mybutton" content="Submit" variant="green" />            </MDBCol>
            </MDBRow>


            <MDBRow>

            </MDBRow>
          </MDBCol>
        </MDBRow>
      </MDBContainer>
    </section>
    <FooterOne />

    </>
  );
}