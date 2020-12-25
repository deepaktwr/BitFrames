package proj.me.bitframedemo.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import proj.me.bitframe.BeanBitFrame;
import proj.me.bitframe.FrameCallback;
import proj.me.bitframe.ImageType;
import proj.me.bitframe.ViewFrame;
import proj.me.bitframe.helper.FrameType;
import proj.me.bitframe.helper.Utils;
import proj.me.bitframedemo.R;
import proj.me.bitframedemo.beans.FrameBean;
import proj.me.bitframedemo.binders.CardBinder;

/**
 * Created by root on 20/9/16.
 */

public class FrameAdapter extends RecyclerView.Adapter<FrameAdapter.ViewHolder>{

    List<FrameBean> frameBeanList;
    int containerWidth;
    int containerHeight;
    public FrameAdapter(int containerWidth, int containerHeight, List<FrameBean> frameBeanList){
        this.frameBeanList = frameBeanList;
        this.containerWidth = containerWidth;
        this.containerHeight = containerHeight;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new ViewHolder(inflater.inflate(R.layout.card_container, parent, false), containerWidth, containerHeight);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FrameBean frameBean = frameBeanList.get(position);
        Utils.logVerbose("IMAGE_LOADING : "+" came to load view frame at "+position);
        holder.cardBinder.viewFrame().clearContainerChilds();
        holder.cardBinder.setTitle(frameBean.getTitle());
        holder.cardBinder.setDescription(frameBean.getDescription());
        holder.cardBinder.viewFrame().showBitFrame(frameBean.getBeanBitFrameList(), holder, FrameType.FRAMED);
    }

    @Override
    public int getItemCount() {
        return frameBeanList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements FrameCallback {
        CardBinder cardBinder = new CardBinder();
        public ViewHolder(View itemView, int containerWidth, int containerHeight) {
            super(itemView);
            cardBinder.bind(itemView);
            cardBinder.viewFrame().setFrameDimensions(0, 0, containerWidth, containerHeight);

            ViewGroup.LayoutParams layoutParams = cardBinder.viewFrame().getLayoutParams();
            layoutParams.width = containerWidth;
            layoutParams.height = containerHeight;
            cardBinder.viewFrame().setLayoutParams(layoutParams);
            cardBinder.viewFrame().invalidate();
        }

        @Override
        public void imageClick(ImageType imageType, int imagePosition, String imageLink, ViewFrame actionableViewFrame) {

        }

        @Override
        public void frameResult(List<BeanBitFrame> beanBitFrameList, ViewFrame actionableViewFrame) {

        }

        @Override
        public void addMoreClick(ViewFrame actionableViewFrame) {

        }

        @Override
        public void containerAdded(int containerWidth, int containerHeight, boolean isAddInLayout, ViewFrame actionableViewFrame) {

        }

        @Override
        public void loadedFrameColors(int lastLoadedFrameColor, int mixedLoadedColor, int inverseMixedLoadedColor, ViewFrame actionableViewFrame) {
            cardBinder.setTitleColor(mixedLoadedColor);
            cardBinder.setDescriptionColor(mixedLoadedColor);
        }
    }
}
