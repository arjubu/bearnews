import Image from "next/image";
import Link from "next/link";
import { slugify } from "../../../utils";

const PostLayoutFive = ({ data, index }) => {
	function renderforme(){
		if(data.featureImg!=null){
		return(<Link href={`/post/${data.slug}`}>
		<a className="align-self-center w-100">
			<Image  
			src={data.featureImg}
			alt={data.title}
			width={600}
			height={90}
			/>
		</a>
	</Link>);}
	return;
	}
  return (
    <div className="media post-block post-block__fluid post-block__mid flex-column m-b-xs-30 m-b-md-40 m-b-lg-40">
		{renderforme()}
      <div className="media-body">
        <div className="post-cat-group m-b-xs-15">
			<Link href={`/category/${slugify(data.cate)}`}>
				<a className={`post-cat cat-btn btn-big ${data.cate_bg ?? "bg-color-blue-one"}`}>{data.cate}</a>
			</Link>
        </div>
        <h3 className="axil-post-title hover-line">
			<Link href={`/post/${data.slug}`}>
				<a>{data.title}</a>
			</Link>
        </h3>
        <p className="mid">
          {data.excerpt}
        </p>
      </div>
	  <div className="border-bottom"></div>
    </div>
  );
};

export default PostLayoutFive;
