import Head from 'next/head'

const HeadMeta = ({metaTitle}) => {
    return ( 
        <Head>
            {/* Basic metas */}
            <meta charSet="utf-8" />
            <meta name="robots" content="noindex, follow" />
            <meta httpEquiv="x-ua-compatible" content="ie=edge" />
            <meta name="description" content="Bearnews - The best place for baylor" />
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
            <title>{`${metaTitle?metaTitle:"Bearnews"} || Bearnews - The best place for baylor`}</title>
            <link rel="icon" type="image/x-icon" href={`${process.env.NODE_ENV === 'production' ? process.env.NEXT_PUBLIC_BASEPATH ?? '' : ''}/Bear_Mark_1_Color_01.jpg`} />
        </Head>
     );
}
 
export default HeadMeta;