import { FacebookShareButton, FacebookIcon } from 'react-share';
const Facebook = ({ url, quotes, hashtag }) => {

return (
<>

  <FacebookShareButton
     url={url}     //eg. https://www.example.com
     quotes={quotes}  //"Your Quotes"
     hashtag={hashtag} // #hashTag
   >
    <FacebookIcon />
  </FacebookShareButton>

</>
)}
export default Facebook;

