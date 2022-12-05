import Like from "./Like"
import Love from "./Love"
import ShareToFB from "./ShareToFB"
const SocialShareBottom = ({slug}) => {
  return (
    <div className="post-shares m-t-xs-60">
      <div className="title">Operation:</div>
      <ul className="social-share social-share__rectangular">
        <li>
        <Like slug={slug}/>
        </li>
        <li>
          <Love slug={slug}/>
        </li>
        <li>
          <ShareToFB slug={slug}/>
        </li>
      </ul>
    </div>
  );
};

export default SocialShareBottom;